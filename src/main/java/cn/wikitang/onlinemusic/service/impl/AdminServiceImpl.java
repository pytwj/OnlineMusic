package cn.wikitang.onlinemusic.service.impl;

import cn.wikitang.onlinemusic.entity.Admin;
import cn.wikitang.onlinemusic.dao.AdminMapper;
import cn.wikitang.onlinemusic.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
