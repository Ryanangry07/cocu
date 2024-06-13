import http from '@/http';

//recommend clubs
export const getRecommendClubsAPI = ()=> {
    return http.get('/club/getRecommendClubs')
}

//recommend activities
export const getRecommendActivitiesAPI = ()=> {
    return http.get('/activity/getRecommendActivities')
}

//notifications
export const getNotificationsAPI = ()=> {
    return http.get('/club/getNotifications')
}


//latest activities
export const getLatestActivitiesAPI = ()=> {
    return http.get('/activity/getLatestActivities')
}

//top getTopActivitiesAPI
export const getTopActivitiesAPI = ()=> {
    return http.get('/activity/getTopActivities')
}
