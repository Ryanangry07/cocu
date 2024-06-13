import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import { ElMessage } from 'element-plus';

const config = {
    // baseURL: 'http://localhost:8082',
    baseURL: '/api',
    timeout: 10000,
    // solve different session problem
    withCredentials: true
}

class Http {
    // axios instance
    private instance: AxiosInstance;
    // initialization
    constructor(config: AxiosRequestConfig) {
        this.instance = axios.create(config);
        // define interceptors
        this.interceptors();
    }
    // interceptors   (before: add token | after: check status code)
    private interceptors() {
        // before axios send request
        this.instance.interceptors.request.use((config: InternalAxiosRequestConfig) => {
            // Get the token from localStorage
            const token = localStorage.getItem('jwtToken');
            if (token) {
                // Set the Authorization header with the Bearer token
                config.headers['Authorization'] = `Bearer ${token}`;
            }
            console.log(config);
            return config;
        }, (error: any) => {
            error.data = {};
            error.data.msg = 'Server internal exception, please contact administrator!';
            return Promise.reject(error);
        });

        // after axios return response
        this.instance.interceptors.response.use((res: AxiosResponse) => {
            // console.log(res.data)
            if (res.data.code != 200) {
                ElMessage.error(res.data.msg || 'Server has problem!');
                return Promise.reject(res.data.msg || 'Server has problem!');
            } else {
                return res.data;
            }
        }, (error) => {
            console.log('Axios request error!');
            error.data = {};
            if (error && error.response) {
                switch (error.response.status) {
                    case 400:
                        error.data.msg = 'Wrong request!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 401:
                        error.data.msg = 'Unauthorized, please login again!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 403:
                        error.data.msg = 'Access denied!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 404:
                        error.data.msg = 'Request error, the requested interface was not found!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 405:
                        error.data.msg = 'Request method not allowed!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 408:
                        error.data.msg = 'Request timeout!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 500:
                        error.data.msg = 'Server error!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 501:
                        error.data.msg = 'Network not implemented!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 502:
                        error.data.msg = 'Network error!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 503:
                        error.data.msg = 'Service unavailable!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 504:
                        error.data.msg = 'Network timeout!';
                        ElMessage.error(error.data.msg);
                        break;
                    case 505:
                        error.data.msg = 'The HTTP version does not support this request!';
                        ElMessage.error(error.data.msg);
                        break;
                    default:
                        error.data.msg = `Connection error ${error.response.status}`;
                        ElMessage.error(error.data.msg);
                }
            } else {
                error.data.msg = 'Failed to connect to the server';
                ElMessage.error(error.data.msg);
            }
            return Promise.reject(error);
        });
    }

    /* GET method */
    get(url: string, params?: object): Promise<any> {
        return this.instance.get(url, { params });
    }
    /* POST method */
    post(url: string, data?: object): Promise<any> {
        return this.instance.post(url, data);
    }
    /* PUT method */
    put(url: string, data?: object): Promise<any> {
        return this.instance.put(url, data);
    }
    /* DELETE method */
    delete(url: string): Promise<any> {
        return this.instance.delete(url);
    }
}

export default new Http(config);
