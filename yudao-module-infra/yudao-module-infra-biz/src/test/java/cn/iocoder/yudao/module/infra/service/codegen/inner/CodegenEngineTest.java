package cn.iocoder.yudao.module.infra.service.codegen.inner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.iocoder.yudao.framework.test.core.ut.BaseMockitoUnitTest;
import cn.iocoder.yudao.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import cn.iocoder.yudao.module.infra.dal.dataobject.codegen.CodegenTableDO;
import cn.iocoder.yudao.module.infra.enums.codegen.*;
import cn.iocoder.yudao.module.infra.framework.codegen.config.CodegenProperties;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link CodegenEngine} 的单元测试
 *
 * @author 芋道源码
 */
public class CodegenEngineTest extends BaseMockitoUnitTest {

    @InjectMocks
    private CodegenEngine codegenEngine;

    @Spy
    private CodegenProperties codegenProperties = new CodegenProperties()
            .setBasePackage("cn.iocoder.yudao.module");

    @BeforeEach
    public void setUp() {
        codegenEngine.initGlobalBindingMap();
    }

    @Test
    public void testExecute_vue3_crud() {
        // 准备请求参数
        CodegenTableDO table = new CodegenTableDO().setScene(CodegenSceneEnum.ADMIN.getScene())
                .setTableName("system_user").setTableComment("用户表")
                .setModuleName("system").setBusinessName("user").setClassName("SystemUser")
                .setClassComment("用户").setAuthor("芋道源码")
                .setTemplateType(CodegenTemplateTypeEnum.CRUD.getType())
                .setFrontType(CodegenFrontTypeEnum.VUE3.getType())
                .setParentMenuId(10L);
        CodegenColumnDO idColumn = new CodegenColumnDO().setColumnName("id").setDataType(JdbcType.BIGINT.name())
                .setColumnComment("编号").setNullable(false).setPrimaryKey(true).setAutoIncrement(true)
                .setOrdinalPosition(1).setJavaType("Long").setJavaField("id").setExample("1024")
                .setCreateOperation(false).setUpdateOperation(true).setListOperation(false)
                .setListOperationResult(true);
        CodegenColumnDO nameColumn = new CodegenColumnDO().setColumnName("name").setDataType(JdbcType.VARCHAR.name())
                .setColumnComment("名字").setNullable(false).setPrimaryKey(false)
                .setOrdinalPosition(2).setJavaType("String").setJavaField("name").setExample("芋头")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(true)
                .setListOperationCondition(CodegenColumnListConditionEnum.LIKE.getCondition()).setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.INPUT.getType());
        CodegenColumnDO avatarColumn = new CodegenColumnDO().setColumnName("avatar").setDataType(JdbcType.VARCHAR.name())
                .setColumnComment("头像").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(3).setJavaType("String").setJavaField("avatar").setExample("https://www.iocoder.cn/1.png")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(false)
                .setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.UPLOAD_IMAGE.getType());
        CodegenColumnDO videoColumn = new CodegenColumnDO().setColumnName("video").setDataType(JdbcType.VARCHAR.name())
                .setColumnComment("视频").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(4).setJavaType("String").setJavaField("video").setExample("https://www.iocoder.cn/1.mp4")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(false)
                .setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.UPLOAD_FILE.getType());
        CodegenColumnDO descriptionColumn = new CodegenColumnDO().setColumnName("description").setDataType(JdbcType.VARCHAR.name())
                .setColumnComment("个人简介").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(5).setJavaType("String").setJavaField("description").setExample("我是介绍")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(false)
                .setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.EDITOR.getType());
        CodegenColumnDO sex1Column = new CodegenColumnDO().setColumnName("sex1").setDataType(JdbcType.VARCHAR.name())
                .setColumnComment("性别 1").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(6).setJavaType("String").setJavaField("sex1").setExample("男")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(true)
                .setListOperationCondition(CodegenColumnListConditionEnum.EQ.getCondition()).setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.SELECT.getType()).setDictType("system_sex1");
        CodegenColumnDO sex2Column = new CodegenColumnDO().setColumnName("sex2").setDataType(JdbcType.INTEGER.name())
                .setColumnComment("性别 2").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(7).setJavaType("Integer").setJavaField("sex2").setExample("1")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(true)
                .setListOperationCondition(CodegenColumnListConditionEnum.EQ.getCondition()).setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.CHECKBOX.getType()).setDictType("system_sex2");
        CodegenColumnDO sex3Column = new CodegenColumnDO().setColumnName("sex3").setDataType(JdbcType.BOOLEAN.name())
                .setColumnComment("性别 3").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(8).setJavaType("Boolean").setJavaField("sex3").setExample("true")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(true)
                .setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.RADIO.getType()).setDictType("system_sex3");
        CodegenColumnDO birthdayColumn = new CodegenColumnDO().setColumnName("birthday").setDataType(JdbcType.DATE.name())
                .setColumnComment("出生日期").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(9).setJavaType("LocalDateTime").setJavaField("birthday")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(true)
                .setListOperationCondition(CodegenColumnListConditionEnum.EQ.getCondition()).setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.DATETIME.getType());
        CodegenColumnDO memoColumn = new CodegenColumnDO().setColumnName("memo").setDataType(JdbcType.VARCHAR.name())
                .setColumnComment("备注").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(10).setJavaType("String").setJavaField("memo").setExample("我是备注")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(false)
                .setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.TEXTAREA.getType());
        CodegenColumnDO createTimeColumn = new CodegenColumnDO().setColumnName("create_time").setDataType(JdbcType.DATE.name())
                .setColumnComment("创建时间").setNullable(true).setPrimaryKey(false)
                .setOrdinalPosition(11).setJavaType("LocalDateTime").setJavaField("createTime")
                .setCreateOperation(true).setUpdateOperation(true).setListOperation(true)
                .setListOperationCondition(CodegenColumnListConditionEnum.BETWEEN.getCondition()).setListOperationResult(true)
                .setHtmlType(CodegenColumnHtmlTypeEnum.DATETIME.getType());
        List<CodegenColumnDO> columns = Arrays.asList(idColumn, nameColumn, avatarColumn, descriptionColumn,
                sex1Column, sex2Column, sex3Column, birthdayColumn, memoColumn, createTimeColumn);

        // 调用
        Map<String, String> result = codegenEngine.execute(table, columns, null, null);

        // 断言
        assertEquals(21, result.size());
        // 断言 vo 类
        for (String vo : new String[]{"SystemUserBaseVO", "SystemUserCreateReqVO", "SystemUserUpdateReqVO", "SystemUserRespVO",
                "SystemUserPageReqVO", "SystemUserExportReqVO", "SystemUserExcelVO"}) {
            assertPathContentEquals("vue3_crud/java/" + vo,
                    result, "yudao-module-system/yudao-module-system-biz/src/main/java/cn/iocoder/yudao/module/module/system/controller/admin/user/vo/" + vo + ".java");
        }
        // 断言 controller 类
        assertPathContentEquals("vue3_crud/java/SystemUserController",
                result, "yudao-module-system/yudao-module-system-biz/src/main/java/cn/iocoder/yudao/module/module/system/controller/admin/user/SystemUserController.java");
        // 断言 service 类
        assertPathContentEquals("vue3_crud/java/SystemUserService",
                result, "yudao-module-system/yudao-module-system-biz/src/main/java/cn/iocoder/yudao/module/module/system/service/user/SystemUserService.java");
        assertPathContentEquals("vue3_crud/java/SystemUserServiceImpl",
                result, "yudao-module-system/yudao-module-system-biz/src/main/java/cn/iocoder/yudao/module/module/system/service/user/SystemUserServiceImpl.java");
        // 断言 convert 类
        assertPathContentEquals("vue3_crud/java/SystemUserConvert",
                result, "yudao-module-system/yudao-module-system-biz/src/main/java/cn/iocoder/yudao/module/module/system/convert/user/SystemUserConvert.java");
        // 断言 enums 类
        assertPathContentEquals("vue3_crud/java/ErrorCodeConstants",
                result, "yudao-module-system/yudao-module-system-api/src/main/java/cn/iocoder/yudao/module/module/system/enums/ErrorCodeConstants_手动操作.java");
        // 断言 dal 类
        assertPathContentEquals("vue3_crud/java/SystemUserDO",
                result, "yudao-module-system/yudao-module-system-biz/src/main/java/cn/iocoder/yudao/module/module/system/dal/dataobject/user/SystemUserDO.java");
        assertPathContentEquals("vue3_crud/java/SystemUserMapper",
                result, "yudao-module-system/yudao-module-system-biz/src/main/java/cn/iocoder/yudao/module/module/system/dal/mysql/user/SystemUserMapper.java");
        assertPathContentEquals("vue3_crud/java/SystemUserMapper_xml",
                result, "yudao-module-system/yudao-module-system-biz/src/main/resources/mapper/user/SystemUserMapper.xml");
        // 断言 test 类
        assertPathContentEquals("vue3_crud/java/SystemUserServiceImplTest",
                result, "yudao-module-system/yudao-module-system-biz/src/test/java/cn/iocoder/yudao/module/module/system/service/user/SystemUserServiceImplTest.java");
        // 断言 sql 语句
        assertPathContentEquals("vue3_crud/sql/h2",
                result, "sql/h2.sql");
        assertPathContentEquals("vue3_crud/sql/sql",
                result, "sql/sql.sql");
        // 断言 vue 语句
        assertPathContentEquals("vue3_crud/vue/index",
                result, "yudao-ui-admin-vue3/src/views/system/user/index.vue");
        assertPathContentEquals("vue3_crud/vue/form",
                result, "yudao-ui-admin-vue3/src/views/system/user/UserForm.vue");
        assertPathContentEquals("vue3_crud/vue/api",
                result, "yudao-ui-admin-vue3/src/api/system/user/index.ts");
//        result.forEach(new BiConsumer<String, String>() {
//            @Override
//            public void accept(String s, String s2) {
//                System.out.println(s);
//                System.out.println(s2);
//                System.out.println("=================");
//            }
//        });
    }

    private void assertPathContentEquals(String path, Map<String, String> result, String key) {
        String pathContent = ResourceUtil.readUtf8Str("codegen/" + path);
        String valueContent = result.get(key);
        assertEquals(pathContent, valueContent);
    }

}