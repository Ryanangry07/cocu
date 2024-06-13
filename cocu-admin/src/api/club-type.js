import request from '@/utils/request'



export function getClubTypes(query, page) {
    const data = {
        pageNumber: page.pageNumber,
        pageSize: page.pageSize,
        data: {
            name: query.name
        }
    }
    return request({
        url: '/club-type/listPage',
        method: 'post',
        data
    })
    /*return request({
        url: '/club-type/listPage',
        method: 'post',
        params: {
            pageNumber: page.pageNumber,
            pageSize: page.pageSize,
            data: {
                name: query.name
            },
            name: page.name,
            sort: page.sort,
            year: query.year,
            month: query.month,
            tagId: query.tagId,
            categoryId: query.categoryId,
            keyword: query.keyword,
            authorId: query.authorId,
            starUid: query.starUid
        }
    })*/
}


export function editType(query, page) {
    return request({
        url: '/club-type/update',
        method: 'get',
        params: {
            pageNumber: page.pageNumber,
            pageSize: page.pageSize,
            name: page.name,
            sort: page.sort,
            year: query.year,
            month: query.month,
            tagId: query.tagId,
            categoryId: query.categoryId,
            keyword: query.keyword,
            authorId: query.authorId,
            starUid: query.starUid
        }
    })
}


export function deleteArticleById(articleId) {
    return request({
        url: `/articles/delete/${articleId}`,
        method: 'get'
    })
}


export function starArticle(articleId, userId) {
    return request({
        url: '/star',
        method: 'get',
        params: {
            articleId: articleId,
            userId: userId
        }
    })
}

export function publishArticle(article) {
    return request({
        url: '/articles/publish',
        method: 'post',
        data: article
    })
}

export function saveCategory(avatar, categoryname, description) {
    const data = {
        avatar,
        categoryname,
        description
    }
    return request({
        url: '/categorys/create',
        method: 'post',
        data
    })
}