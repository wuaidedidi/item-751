/**
 * UI组件库
 * 包含Toast、Modal、Confirm等组件
 */

// ==================== Toast 组件 ====================
class ToastManager {
    constructor() {
        this.container = null;
        this.init();
    }

    init() {
        this.container = document.createElement('div');
        this.container.className = 'toast-container';
        document.body.appendChild(this.container);
    }

    show(options) {
        const { type = 'info', title, message, duration = 3000 } = options;
        
        const icons = {
            success: '✓',
            error: '✕',
            warning: '⚠',
            info: 'ℹ'
        };

        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.innerHTML = `
            <span class="toast-icon">${icons[type]}</span>
            <div class="toast-content">
                <div class="toast-title">${title || this.getDefaultTitle(type)}</div>
                ${message ? `<div class="toast-message">${message}</div>` : ''}
            </div>
            <button class="toast-close">✕</button>
        `;

        this.container.appendChild(toast);

        // 关闭按钮
        toast.querySelector('.toast-close').addEventListener('click', () => {
            this.close(toast);
        });

        // 自动关闭
        if (duration > 0) {
            setTimeout(() => this.close(toast), duration);
        }

        return toast;
    }

    getDefaultTitle(type) {
        const titles = {
            success: '操作成功',
            error: '操作失败',
            warning: '警告',
            info: '提示'
        };
        return titles[type] || '提示';
    }

    close(toast) {
        toast.classList.add('closing');
        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 300);
    }

    success(message, title) {
        return this.show({ type: 'success', message, title });
    }

    error(message, title) {
        return this.show({ type: 'error', message, title });
    }

    warning(message, title) {
        return this.show({ type: 'warning', message, title });
    }

    info(message, title) {
        return this.show({ type: 'info', message, title });
    }
}

// ==================== Modal 组件 ====================
class ModalManager {
    constructor() {
        this.modals = [];
    }

    create(options) {
        const {
            title = '',
            content = '',
            width = '560px',
            showClose = true,
            footer = null,
            onClose = null
        } = options;

        const overlay = document.createElement('div');
        overlay.className = 'modal-overlay';
        overlay.innerHTML = `
            <div class="modal" style="max-width: ${width}">
                <div class="modal-header">
                    <h3 class="modal-title">${title}</h3>
                    ${showClose ? '<button class="modal-close">✕</button>' : ''}
                </div>
                <div class="modal-body">${content}</div>
                ${footer ? `<div class="modal-footer">${footer}</div>` : ''}
            </div>
        `;

        document.body.appendChild(overlay);

        // 动画显示
        requestAnimationFrame(() => {
            overlay.classList.add('active');
        });

        // 关闭按钮
        const closeBtn = overlay.querySelector('.modal-close');
        if (closeBtn) {
            closeBtn.addEventListener('click', () => this.close(overlay, onClose));
        }

        // 点击遮罩关闭
        overlay.addEventListener('click', (e) => {
            if (e.target === overlay) {
                this.close(overlay, onClose);
            }
        });

        // ESC关闭
        const escHandler = (e) => {
            if (e.key === 'Escape') {
                this.close(overlay, onClose);
                document.removeEventListener('keydown', escHandler);
            }
        };
        document.addEventListener('keydown', escHandler);

        this.modals.push(overlay);

        return {
            element: overlay,
            close: () => this.close(overlay, onClose),
            getBody: () => overlay.querySelector('.modal-body'),
            getFooter: () => overlay.querySelector('.modal-footer')
        };
    }

    close(overlay, callback) {
        overlay.classList.remove('active');
        setTimeout(() => {
            if (overlay.parentNode) {
                overlay.parentNode.removeChild(overlay);
            }
            const index = this.modals.indexOf(overlay);
            if (index > -1) {
                this.modals.splice(index, 1);
            }
            if (callback) callback();
        }, 300);
    }

    closeAll() {
        this.modals.forEach(overlay => {
            overlay.classList.remove('active');
            setTimeout(() => {
                if (overlay.parentNode) {
                    overlay.parentNode.removeChild(overlay);
                }
            }, 300);
        });
        this.modals = [];
    }
}

// ==================== Confirm 组件 ====================
class ConfirmManager {
    constructor(modalManager) {
        this.modalManager = modalManager;
    }

    show(options) {
        return new Promise((resolve) => {
            const {
                type = 'warning',
                title = '确认操作',
                message = '确定要执行此操作吗？',
                confirmText = '确定',
                cancelText = '取消',
                confirmClass = 'btn-primary'
            } = options;

            const icons = {
                warning: '⚠',
                danger: '✕',
                success: '✓',
                info: 'ℹ'
            };

            const content = `
                <div class="confirm-icon ${type}">${icons[type]}</div>
                <h4 class="confirm-title">${title}</h4>
                <p class="confirm-message">${message}</p>
            `;

            const footer = `
                <button class="btn btn-secondary cancel-btn">${cancelText}</button>
                <button class="btn ${type === 'danger' ? 'btn-danger' : confirmClass} confirm-btn">${confirmText}</button>
            `;

            const modal = this.modalManager.create({
                title: '',
                content,
                footer,
                width: '420px',
                showClose: false,
                onClose: () => resolve(false)
            });

            modal.element.classList.add('confirm-modal');
            modal.element.querySelector('.modal-header').style.display = 'none';

            // 取消按钮
            modal.element.querySelector('.cancel-btn').addEventListener('click', () => {
                modal.close();
                resolve(false);
            });

            // 确认按钮
            modal.element.querySelector('.confirm-btn').addEventListener('click', () => {
                modal.close();
                resolve(true);
            });
        });
    }

    warning(message, title) {
        return this.show({ type: 'warning', title, message });
    }

    danger(message, title) {
        return this.show({ type: 'danger', title, message, confirmClass: 'btn-danger' });
    }

    delete(itemName = '此项') {
        return this.show({
            type: 'danger',
            title: '确认删除',
            message: `确定要删除${itemName}吗？此操作不可撤销。`,
            confirmText: '删除',
            confirmClass: 'btn-danger'
        });
    }
}

// ==================== Loading 组件 ====================
class LoadingManager {
    constructor() {
        this.overlay = null;
    }

    show(message = '加载中...') {
        if (this.overlay) return;

        this.overlay = document.createElement('div');
        this.overlay.className = 'loading-overlay';
        this.overlay.innerHTML = `
            <div style="text-align: center;">
                <div class="loading-spinner"></div>
                <p style="margin-top: 16px; color: var(--text-secondary);">${message}</p>
            </div>
        `;
        document.body.appendChild(this.overlay);
    }

    hide() {
        if (this.overlay && this.overlay.parentNode) {
            this.overlay.parentNode.removeChild(this.overlay);
            this.overlay = null;
        }
    }
}

// ==================== 创建全局实例 ====================
const Toast = new ToastManager();
const Modal = new ModalManager();
const Confirm = new ConfirmManager(Modal);
const Loading = new LoadingManager();

// 导出全局
window.Toast = Toast;
window.Modal = Modal;
window.Confirm = Confirm;
window.Loading = Loading;
