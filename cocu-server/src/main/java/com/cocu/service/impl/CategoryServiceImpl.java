package com.cocu.service.impl;

import com.cocu.entity.Category;
import com.cocu.mapper.CategoryMapper;
import com.cocu.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
