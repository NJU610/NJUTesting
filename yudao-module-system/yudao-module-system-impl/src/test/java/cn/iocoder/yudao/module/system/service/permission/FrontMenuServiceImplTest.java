package cn.iocoder.yudao.module.system.service.permission;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;
import cn.iocoder.yudao.module.system.dal.mysql.permission.FrontMenuMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.RoleFrontMenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import javax.naming.NameNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomString;
import static org.junit.jupiter.api.Assertions.*;

@Import(FrontMenuServiceImpl.class)
class FrontMenuServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FrontMenuMapper frontMenuMapper;

    @Resource
    private RoleFrontMenuMapper roleFrontMenuMapper;

    @Resource
    private FrontMenuService  frontMenuService;

    @Test
    void createFrontMenu() {

        FrontMenuCreateReqVO frontMenuCreateReqVO = randomPojo(FrontMenuCreateReqVO.class, o->{
            o.setName(randomString());
            o.setHideInMenu(1);
        });

        frontMenuService.createFrontMenu(frontMenuCreateReqVO);

        assertEquals(frontMenuMapper.selectCount(),1);

    }

    @Test
    void updateFrontMenu() {

        FrontMenuDO fro = FrontMenuDO.builder()
                .id(1L)
                .hideInMenu(1)
                .status(1)
                .build();

        frontMenuMapper.insert(fro);

        FrontMenuUpdateReqVO updateReqVO = randomPojo(FrontMenuUpdateReqVO.class,o->{
            o.setId(1L);
            o.setHideInMenu(2);
        });

        frontMenuService.updateFrontMenu(updateReqVO);

        assertEquals(frontMenuMapper.selectById(1).getHideInMenu(),updateReqVO.getHideInMenu());
    }

    @Test
    void deleteFrontMenu() {
        FrontMenuDO fro = FrontMenuDO.builder()
                .id(1L)
                .hideInMenu(1)
                .status(1)
                .build();

        frontMenuMapper.insert(fro);

        frontMenuService.deleteFrontMenu(1L);

        assertEquals(frontMenuMapper.selectCount(),0);
    }

    @Test
    void getFrontMenu() {

        FrontMenuDO fro = FrontMenuDO.builder()
                .id(1L)
                .hideInMenu(1)
                .status(1)
                .build();

        frontMenuMapper.insert(fro);

        assertNotNull(frontMenuService.getFrontMenu(1L));
    }

    @Test
    void getFrontMenuList() {

        FrontMenuDO fro = FrontMenuDO.builder()
                .id(1L)
                .hideInMenu(1)
                .status(1)
                .build();

        frontMenuMapper.insert(fro);

        assertNotNull(frontMenuService.getFrontMenuList(Collections.singletonList(1L)));

    }

    @Test
    void getFrontMenuPage() {

        FrontMenuDO fro = FrontMenuDO.builder()
                .id(1L)
                .hideInMenu(1)
                .status(1)
                .build();

        frontMenuMapper.insert(fro);

        FrontMenuPageReqVO pageReqVO = randomPojo(FrontMenuPageReqVO.class,o->{
            o.setId(1L);
        });

        assertNotNull(frontMenuService.getFrontMenuPage(pageReqVO));
    }

    @Test
    void testGetFrontMenuList() {
        FrontMenuDO fro = FrontMenuDO.builder()
                .id(1L)
                .hideInMenu(1)
                .status(1)
                .build();

        frontMenuMapper.insert(fro);

        FrontMenuExportReqVO exportReqVO = randomPojo(FrontMenuExportReqVO.class, o->{
            o.setId(1L);
        });

        assertNotNull(frontMenuService.getFrontMenuList(exportReqVO));
    }


    @Test
    void validFrontMenus() {
        FrontMenuDO fro = FrontMenuDO.builder()
                .id(1L)
                .hideInMenu(1)
                .status(0)
                .build();

        frontMenuMapper.insert(fro);

        frontMenuService.validFrontMenus(Collections.singletonList(1L));
    }

    @Test
    void getFrontMenuListByStatus() {
    }
}