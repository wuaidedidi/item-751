/**
 * API请求封装
 * 统一处理请求和响应
 */
const API_BASE_URL = '';

// Token管理
const TokenManager = {
    getToken() {
        return localStorage.getItem('ems_token');
    },
    setToken(token) {
        localStorage.setItem('ems_token', token);
    },
    removeToken() {
        localStorage.removeItem('ems_token');
    },
    getUser() {
        const user = localStorage.getItem('ems_user');
        return user ? JSON.parse(user) : null;
    },
    setUser(user) {
        localStorage.setItem('ems_user', JSON.stringify(user));
    },
    removeUser() {
        localStorage.removeItem('ems_user');
    },
    clear() {
        this.removeToken();
        this.removeUser();
    }
};

// API请求类
class ApiClient {
    constructor(baseUrl) {
        this.baseUrl = baseUrl;
    }

    async request(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;
        const token = TokenManager.getToken();

        const config = {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...options.headers,
            },
        };

        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }

        if (options.body && typeof options.body === 'object') {
            config.body = JSON.stringify(options.body);
        }

        try {
            const response = await fetch(url, config);
            const data = await response.json();

            if (response.status === 401) {
                // Token过期，清除登录状态
                TokenManager.clear();
                window.location.href = '/login.html';
                throw new Error(data.message || '登录已过期，请重新登录');
            }

            if (!response.ok || data.code !== 200) {
                throw new Error(data.message || '请求失败');
            }

            return data;
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    get(endpoint, params = {}) {
        // Filter out null and undefined values
        const filteredParams = Object.fromEntries(
            Object.entries(params).filter(([_, v]) => v !== null && v !== undefined && v !== '')
        );
        const queryString = new URLSearchParams(filteredParams).toString();
        const url = queryString ? `${endpoint}?${queryString}` : endpoint;
        return this.request(url, { method: 'GET' });
    }

    post(endpoint, body) {
        return this.request(endpoint, { method: 'POST', body });
    }

    put(endpoint, body) {
        return this.request(endpoint, { method: 'PUT', body });
    }

    delete(endpoint) {
        return this.request(endpoint, { method: 'DELETE' });
    }
}

// 创建API实例
const api = new ApiClient(API_BASE_URL);

// API接口
const AuthAPI = {
    login: (data) => api.post('/api/auth/login', data),
    register: (data) => api.post('/api/auth/register', data),
    getUserInfo: () => api.get('/api/auth/info'),
};

const EmployeeAPI = {
    getList: (params) => api.get('/api/employees', params),
    getById: (id) => api.get(`/api/employees/${id}`),
    create: (data) => api.post('/api/employees', data),
    update: (id, data) => api.put(`/api/employees/${id}`, data),
    delete: (id) => api.delete(`/api/employees/${id}`),
    getStatistics: () => api.get('/api/employees/statistics'),
};

const DepartmentAPI = {
    getList: () => api.get('/api/departments'),
    getTree: () => api.get('/api/departments/tree'),
    getById: (id) => api.get(`/api/departments/${id}`),
    create: (data) => api.post('/api/departments', data),
    update: (id, data) => api.put(`/api/departments/${id}`, data),
    delete: (id) => api.delete(`/api/departments/${id}`),
};

const PositionAPI = {
    getList: () => api.get('/api/positions'),
    getById: (id) => api.get(`/api/positions/${id}`),
    create: (data) => api.post('/api/positions', data),
    update: (id, data) => api.put(`/api/positions/${id}`, data),
    delete: (id) => api.delete(`/api/positions/${id}`),
};

const DashboardAPI = {
    getData: () => api.get('/api/dashboard'),
};

// 导出
window.TokenManager = TokenManager;
window.api = api;
window.AuthAPI = AuthAPI;
window.EmployeeAPI = EmployeeAPI;
window.DepartmentAPI = DepartmentAPI;
window.PositionAPI = PositionAPI;
window.DashboardAPI = DashboardAPI;
