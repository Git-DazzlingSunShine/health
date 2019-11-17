// request拦截器：对请求参数做监听和处理
axios.interceptors.request.use(
    config => {
        let token = localStorage.getItem("token");
        if (token) {
            config.headers.token= token
        }
        return config;
    },
    error => {//请求错误处理
        $message.error({message: '请求超时!'});
        return Promise.resolve(err);
    }
);

axios.interceptors.response.use(data => {
    if (data.status && data.status == 200 && data.data.status == 'error') {
        $message.error({message: data.data.msg});
        return;
    }
    return data;
}, err => {
    if (err.response.status == 504 || err.response.status == 404) {
        $message.error({message: '服务器被吃了⊙﹏⊙∥'});
    } else if (err.response.status == 403) {
        $message.error({message: '权限不足,请联系管理员!'});
    } else {
        $message.error({message: '未知错误!'});
    }
    return Promise.resolve(err);
});
