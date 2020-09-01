import axios from 'axios';

// 请求超时时间
axios.defaults.timeout = 10000;
// 允许跨域
axios.defaults.withCredentials = true;
// 设置默认的Content-Type
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

// axios请求拦截器，可以统一给所有axios请求加上相同的参数，也可以统一添加token
axios.interceptors.request.use(
    config => {
        if (config.data && config.data.type == 'rpc' && config.method == 'post') {
            // Content-Type的默认配置
            axios.defaults.headers.post['Content-Type'] = 'application/json';
            config.headers.post["X-Service-Id"] = config.data.bean;
            config.headers.post["X-Service-Method"] = config.data.method;
        }
        return config;
    },
    error => {
        return Promise.error(error);
    }
);

const get = (url, params) => {
    return new Promise((resolve, reject) => {
        axios.get(url, params).then(res => {
            resolve(res.data);
        }).catch(error => {
            reject(error);
        });
    });
}

const quickGet = (url) => {
    return new Promise((resolve, reject) => {
        axios.get(url).then(res => {
            resolve(res.data);
        }).catch(error => {
            reject(error);
        });
    });
}

const post = (url, params) => {
    return new Promise((resolve, reject) => {
        axios.post(url, params).then(res => {
            resolve(res.data);
        }).catch(error => {
            reject(error);
        });
    });
}

const postForm = (url, params) => {
    return new Promise((resolve, reject) => {
        axios.post(url, params, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            }
        }).then(res => {
            resolve(res.data);
        }).catch(error => {
            reject(error);
        });
    });
}

const put = (url, params) => {
    return new Promise((resolve, reject) => {
        axios.put(url, params).then(res => {
            resolve(res.data);
        }).catch(error => {
            reject(error);
        });
    });
}

const del = (url, params) => {
    return new Promise((resolve, reject) => {
        axios.delete(url, params).then(res => {
            resolve(res.data);
        }).catch(error => {
            reject(error);
        });
    });
}

export default {
    get,
    quickGet,
    post,
    postForm,
    put,
    del
}