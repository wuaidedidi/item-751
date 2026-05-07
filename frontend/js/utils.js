/**
 * 工具函数库
 */

// 日期格式化
function formatDate(date, format = 'YYYY-MM-DD') {
    if (!date) return '-';
    const d = new Date(date);
    if (isNaN(d.getTime())) return '-';
    
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    const hours = String(d.getHours()).padStart(2, '0');
    const minutes = String(d.getMinutes()).padStart(2, '0');
    const seconds = String(d.getSeconds()).padStart(2, '0');
    
    return format
        .replace('YYYY', year)
        .replace('MM', month)
        .replace('DD', day)
        .replace('HH', hours)
        .replace('mm', minutes)
        .replace('ss', seconds);
}

// 获取性别文本
function getGenderText(gender) {
    return gender === 1 ? '男' : gender === 0 ? '女' : '-';
}

// 获取状态文本
function getStatusText(status) {
    return status === 1 ? '正常' : '禁用';
}

// 获取员工状态文本
function getEmployeeStatusText(status) {
    return status === 1 ? '在职' : '离职';
}

// 获取状态徽章HTML
function getStatusBadge(status, type = 'default') {
    if (type === 'employee') {
        return status === 1 
            ? '<span class="badge badge-success">在职</span>'
            : '<span class="badge badge-danger">离职</span>';
    }
    return status === 1 
        ? '<span class="badge badge-success">正常</span>'
        : '<span class="badge badge-danger">禁用</span>';
}

// 生成分页HTML
function generatePagination(pageNum, pageSize, total) {
    const totalPages = Math.ceil(total / pageSize);
    if (totalPages <= 1) return '';
    
    let html = '<div class="pagination">';
    
    // 上一页
    html += `<button class="pagination-btn" data-page="${pageNum - 1}" ${pageNum <= 1 ? 'disabled' : ''}>
        ‹
    </button>`;
    
    // 页码
    const range = 2;
    let startPage = Math.max(1, pageNum - range);
    let endPage = Math.min(totalPages, pageNum + range);
    
    if (startPage > 1) {
        html += `<button class="pagination-btn" data-page="1">1</button>`;
        if (startPage > 2) {
            html += `<span class="pagination-info">...</span>`;
        }
    }
    
    for (let i = startPage; i <= endPage; i++) {
        html += `<button class="pagination-btn ${i === pageNum ? 'active' : ''}" data-page="${i}">${i}</button>`;
    }
    
    if (endPage < totalPages) {
        if (endPage < totalPages - 1) {
            html += `<span class="pagination-info">...</span>`;
        }
        html += `<button class="pagination-btn" data-page="${totalPages}">${totalPages}</button>`;
    }
    
    // 下一页
    html += `<button class="pagination-btn" data-page="${pageNum + 1}" ${pageNum >= totalPages ? 'disabled' : ''}>
        ›
    </button>`;
    
    html += `<span class="pagination-info">共 ${total} 条</span>`;
    html += '</div>';
    
    return html;
}

// 表单数据收集
function getFormData(formElement) {
    const formData = new FormData(formElement);
    const data = {};
    for (let [key, value] of formData.entries()) {
        // 处理数字类型
        if (value === '') {
            data[key] = null;
        } else if (!isNaN(value) && value.trim() !== '') {
            data[key] = Number(value);
        } else {
            data[key] = value;
        }
    }
    return data;
}

// 填充表单
function fillForm(formElement, data) {
    if (!formElement || !data) return;
    
    Object.keys(data).forEach(key => {
        const input = formElement.querySelector(`[name="${key}"]`);
        if (input) {
            if (input.type === 'checkbox') {
                input.checked = !!data[key];
            } else if (input.type === 'radio') {
                const radio = formElement.querySelector(`[name="${key}"][value="${data[key]}"]`);
                if (radio) radio.checked = true;
            } else {
                input.value = data[key] !== null && data[key] !== undefined ? data[key] : '';
            }
        }
    });
}

// 表单验证
function validateForm(formElement) {
    const requiredFields = formElement.querySelectorAll('[required]');
    let isValid = true;
    
    requiredFields.forEach(field => {
        const value = field.value.trim();
        const errorElement = field.parentElement.querySelector('.form-error');
        
        if (!value) {
            isValid = false;
            field.classList.add('error');
            if (errorElement) {
                errorElement.textContent = '此字段为必填项';
                errorElement.style.display = 'block';
            }
        } else {
            field.classList.remove('error');
            if (errorElement) {
                errorElement.style.display = 'none';
            }
        }
    });
    
    return isValid;
}

// 防抖函数
function debounce(func, wait = 300) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// 节流函数
function throttle(func, limit = 300) {
    let inThrottle;
    return function(...args) {
        if (!inThrottle) {
            func.apply(this, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// 检查登录状态
function checkAuth() {
    const token = TokenManager.getToken();
    if (!token) {
        window.location.href = '/login.html';
        return false;
    }
    return true;
}

// 退出登录
function logout() {
    TokenManager.clear();
    window.location.href = '/login.html';
}

// 获取用户首字母
function getInitials(name) {
    if (!name) return 'U';
    return name.charAt(0).toUpperCase();
}

// 导出
window.formatDate = formatDate;
window.getGenderText = getGenderText;
window.getStatusText = getStatusText;
window.getEmployeeStatusText = getEmployeeStatusText;
window.getStatusBadge = getStatusBadge;
window.generatePagination = generatePagination;
window.getFormData = getFormData;
window.fillForm = fillForm;
window.validateForm = validateForm;
window.debounce = debounce;
window.throttle = throttle;
window.checkAuth = checkAuth;
window.logout = logout;
window.getInitials = getInitials;
