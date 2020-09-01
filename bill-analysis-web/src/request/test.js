import http from './http';

const testMock = (params) => {
    return http.post('/bship-bf/test', params);
}

const testRpc = (params) => {
    return http.post('/bship-bf/*.jsonRequest', params);
}

export default {
    testMock, // 测试请求mock数据
    testRpc // 测试Rpc请求
}