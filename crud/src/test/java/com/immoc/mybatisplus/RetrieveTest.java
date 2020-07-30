package com.immoc.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.imooc.mybatisplus.Application;
import com.imooc.mybatisplus.dao.UserMapper;
import com.imooc.mybatisplus.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/30 11:42 上午
 * <p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RetrieveTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据主键Id查询
     */
    @Test
    public void selectById() {
        User user = userMapper.selectById(1094590409767661570L);
        System.out.println(user);
    }

    /**
     * 一次使用多个Id进行查询
     */
    @Test
    public void selectIds() {
        List<Long> idList = Arrays.asList(1088248166370832385L, 1288460195017072641L, 1094590409767661570L);
        List<User> userList = userMapper.selectBatchIds(idList);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用Mao存放查询字段和条件来进行查询
     */
    @Test
    public void selectByMap() {
        //Map存放查询条件，注意key存放的是数据库的字段名，而不是实体中的变量名
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "王天风");
        columnMap.put("age", 25);
        List<User> userList = userMapper.selectByMap(columnMap);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 需求：
     * 名字中包含"雨"，并且年龄小于40
     * sql: name like %雨% and age < 40
     */
    @Test
    public void selectByWrapper() {
        //直接创建一个条件构造器，获取使用Wrappers工具类
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper
                //模糊查询，注意这里的字段都是数据库字段，而不是实体的变量名
                .like("name", "雨")
                //小于
                .lt("age", 40);
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 需求：
     * 名字中包含"雨"，并且年龄大于等于20，且小于等于40，并且email不为空
     * sql: name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectByWrapper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //模糊查询，注意这里的字段都是数据库字段，而不是实体的变量名
                .like("name", "雨")
                //年龄大于20，并且小于40
                .between("age", 20, 40)
                //不为null
                .isNotNull("email");
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 需求：
     * 名字为王姓，或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age>=25 order by age desc,id asc
     */
    @Test
    public void selectByWrapper3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //模糊查询，只包含右边一个%，注意这里的字段都是数据库字段，而不是实体的变量名
                .likeRight("name", "王")
                .or()
                //年龄大于等于25
                .ge("age", 25)
                //先按年龄降序排（从大到小）
                .orderByDesc("age")
                //年龄相同的，再按id的升序排（从小到大）
                .orderByAsc("id");
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 创建日期为2019年2月14日，并且直属上级为名字为王姓
     * sql: date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void selectByWrapper4() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //直接用，不使用占位符，可能会有sql注入的风险
                //.apply("date_format(create_time,'%Y-%m-%d')=2019-02-14")
                //apply占位符查询，目的是为了防止sql注入
                .apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14")
                //inSql子查询
                .inSql("manager_id", "select id from mp_user where name like '王%'");
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     * sql: name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectByWrapper5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王")
                //函数式编程
                //.and(wrapper-> wrapper.lt("age", 40).or().isNotNull("email"))
                .and(new Function<QueryWrapper<User>, QueryWrapper<User>>() {
                    @Override
                    public QueryWrapper<User> apply(QueryWrapper<User> wrapper) {
                        //年龄小于40
                        return wrapper.lt("age", 40)
                                //或者邮箱不为空
                                .or()
                                .isNotNull("email");
                    }
                });

        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 名字为王姓，或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * sql: name like '王%' or (age<40 and age>20 and email is not null)
     */
    @Test
    public void selectByWrapper6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //王姓开头
                .likeRight("name", "王")
                //or()使用Function参数的，将获取年龄小于40，并且年龄大于20
                .or(wrapper -> wrapper.lt("age", 40)
                        .gt("age", 20)
                        //并且邮箱不为空的条件用括号包裹起来
                        .isNotNull("email"));
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * （年龄小于40或邮箱不为空）并且名字为王姓
     * sql: (age<40 or email is not null) and name like '王%'
     */
    @Test
    public void selectByWrapper7() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //nested()，嵌套，就是加括号
                .nested(wrapper -> wrapper.lt("age", 40).or().isNotNull("email"))
                //邮箱不为null
                .likeRight("name", "王");
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 年龄为30、31、34、35
     * sql: age in (30、31、34、35)
     */
    @Test
    public void selectByWrapper8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .in("age", Arrays.asList(30, 31, 34, 35));
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 9、只返回满足条件的其中一条语句即可
     * sql: limit 1
     */
    @Test
    public void selectByWrapper9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //字符串拼接到sql，会有sql注入的风险
        queryWrapper.last("limit 1");
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 需求：
     * select中字段不全部出现的查询，例如只查询出id和姓名（默认会查询出实体中的所有字段）
     * sql: select id,name from user where name like '%雨%' and age<40
     */
    @Test
    public void selectByWrapperSupper() {
        //直接创建一个条件构造器，获取使用Wrappers工具类
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper
                //<重点>，相比上面的selectByWrapper，多了调用select()方法，传入需要查询的列名
                .select("id", "name")
                //模糊查询，注意这里的字段都是数据库字段，而不是实体的变量名
                .like("name", "雨")
                //小于
                .lt("age", 40);
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用条件构造器进行查询
     * <p>
     * 需求：
     * select中字段不全部出现的查询，例如只查询出id和姓名、年龄、邮箱（默认会查询出实体中的所有字段）
     * sql: select id,name,age,email from user where name like '%雨%' and age<40
     */
    @Test
    public void selectByWrapperSupper2() {
        //直接创建一个条件构造器，获取使用Wrappers工具类
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper
                //模糊查询，注意这里的字段都是数据库字段，而不是实体的变量名
                .like("name", "雨")
                //小于
                .lt("age", 40)
                //如果字段比较多，我们每个都写上会比较麻烦，我们可以使用排除法，毕竟只是去掉少量的字段，其他字段都保留
                //参数一：实体类的Class
                //参数二：Predicate函数式接口，test()方法返回boolean，表示是否保留当前遍历到的字段，返回true代表需要，false代表不需要
                .select(User.class, info -> !info.getColumn().equals("create_time")
                        && !info.getColumn().equals("manager_id"));
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 测试动态条件
     */
    @Test
    public void testCondition() {
        String name = "王";
        String email = "";
        condition(name, email);
    }

    /**
     * 查询条件，条件可传可不传
     *
     * @param name  姓名
     * @param email 邮箱
     */
    private void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //手动判空后，加入条件
//        if (StringUtils.isNotEmpty(name)) {
//            queryWrapper.like("name", name);
//        }
//        if (StringUtils.isNotEmpty(email)) {
//            queryWrapper.like("email", email);
//        }
        //上面不够优雅，代码量大
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name)
                .like(StringUtils.isNotEmpty(email), "email", email);
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 使用实体Entity中的字段作为查询条件
     */
    @Test
    public void selectByWrapperEntity() {
        //使用实体作为查询条件加入到where中
        User whereUser = new User();
        whereUser.setReadName("刘雨红");
        whereUser.setAge(32);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);
        //再给查询条件加like等操作也是可以的
        queryWrapper
                .like("name", "雨")
                .lt("age", 40);
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User result : userList) {
            System.out.println(result);
        }
    }

    /**
     * 使用map作为sql的查询条件，map中的所有非空属性会作为sql的等于条件来拼接
     */
    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王天风");
        //age参数，如果为null，会给生成的sql加上age is null，如果想过滤掉为null的属性字段，则将allEq的null2IsNull属性设置为false，默认为true
        params.put("age", null);
        //queryWrapper.allEq(params, false);

        //函数式方式，传入BiPredicate过滤器，test()方法返回当前遍历到的键值对是否加入到条件中，返回true表示加入条件中，返回false代表不加入到条件中
        queryWrapper.allEq(new BiPredicate<String, Object>() {
            @Override
            public boolean test(String key, Object value) {
                //例如过滤掉name的字段
                return !key.equals("name");
            }
        }, params);
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User result : userList) {
            System.out.println(result);
        }
    }

    /**
     * 查询，返回列表，但列表里面的元素不是实体，而是一个Map，每个Map就是一条记录的所有属性以键值对的形式存在
     * 当我们查询的字段相比实体字段少很多的时候，使用实体去存放，会有很多属性是null，不是很优雅，我们使用Map存放会更加明确有什么属性和值
     */
    @Test
    public void selectByWrapperMaps() {
        //直接创建一个条件构造器，获取使用Wrappers工具类
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper
                .select("id", "name")
                //模糊查询，注意这里的字段都是数据库字段，而不是实体的变量名
                .like("name", "雨")
                //小于
                .lt("age", 40);
        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        for (Map<String, Object> map : userList) {
            System.out.println(map);
        }
    }

    /**
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组。
     * sql:
     * select avg(age) avg_age,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) <500
     */
    @Test
    public void selectByWrapperMaps2() {
        //直接创建一个条件构造器，获取使用Wrappers工具类
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper
                //字段起别名：数据库字段名 别名
                .select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age) < {0}", 500);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        for (Map<String, Object> map : userList) {
            System.out.println(map);
        }
    }

    /**
     * selectObjs()，只拿出数据的第一列的数据，其他列都被舍弃（sql中是会查询其他字段的，但是selectObjs()方法只选择第一列的数据）
     * 场景：只返回第一列的时候可以用它
     */
    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "name")
                //模糊查询，注意这里的字段都是数据库字段，而不是实体的变量名
                .like("name", "雨")
                //小于
                .lt("age", 40);
        List<Object> userList = userMapper.selectObjs(queryWrapper);
        for (Object o : userList) {
            System.out.println(o);
        }
    }

    /**
     * 统计查询
     */
    @Test
    public void selectByWrapperCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //会帮我们在sql中添加 COUNT( 1 )，所以我们就不能使用select()方法来指定要查询的列了，否则会作为count的参数来使用
                //.select("id", "name")
                //除非你想count其他字段，就可以使用
                .select("id")
                //模糊查询，注意这里的字段都是数据库字段，而不是实体的变量名
                .like("name", "雨")
                //小于
                .lt("age", 40);
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println("count：" + count);
    }

    /**
     * 只查询出1条数据（必须查询结果只有1条，多条会报错）
     */
    @Test
    public void selectByWrapperOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "name")
                //模糊查询，注意这里的字段都是数据库字段，而不是实体的变量名
                .eq("name", "刘雨红");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     * Lambda条件构造器
     * 好处：仿误写，如果是普通方式，传入数据库字段名，如果写错了就会报错，Lambda表达式使用方法引用来获取字段信息
     */
    @Test
    public void selectByWrapperLambda() {
        //Lambda条件构造器的3种创建方式
//        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                //where name like '%雨%'
                .like(User::getReadName, "雨")
                //and age < 40
                .lt(User::getAge, 40);
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 查询，姓名为王姓，并且（年龄小于40岁或者邮箱不为空）
     */
    @Test
    public void selectByWrapperLambda2() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                //where name like '%王%'
                .likeRight(User::getReadName, "王")
                //and (age < 40 or email is not null)
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 查询，姓名为王姓，并且年龄大于等于20
     */
    @Test
    public void selectByWrapperLambda3() {
        LambdaQueryChainWrapper<User> chainWrapper = new LambdaQueryChainWrapper<>(userMapper);
        List<User> userList = chainWrapper
                //姓名为王姓
                .like(User::getReadName, "雨")
                //年龄大于等于20
                .ge(User::getAge, 20)
                .list();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 自定义sql
     */
    @Test
    public void selectMy() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery
                //where name like '%王%'
                .likeRight(User::getReadName, "王")
                //and (age < 40 or email is not null)
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> userList = userMapper.selectAll(lambdaQuery);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 分页查询，分页对象的泛型是实体类
     */
    @Test
    public void selectPage() {
        //直接创建一个条件构造器，获取使用Wrappers工具类
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 26);
        Page<User> page = new Page<>(1, 2);

        //分页
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);

        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        List<User> userList = iPage.getRecords();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 第二种分页方式，分页对象的泛型是Map
     */
    @Test
    public void selectPage2() {
        //直接创建一个条件构造器，获取使用Wrappers工具类
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 26);
        Page<User> page = new Page<>(1, 2);

        //第二种分页，但返回的IPage泛型类型是Map类型，就是将实体类的字段和值封装到了Map中
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);

        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        List<Map<String, Object>> userList = iPage.getRecords();
        for (Map<String, Object> map : userList) {
            System.out.println(map);
        }
    }

    /**
     * 分页，不查询总记录数（默认会查，会查询总数和分页查询，会查询2次，例如不断上拉加载的场景是不需要查总记录数的，就可以不进行查询）
     */
    @Test
    public void selectPage3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 26);
        //isSearchCount传false，代表不进行查询总记录数，少一次查询
        Page<User> page = new Page<>(1, 2, false);

        //分页
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);

        System.out.println("总页数：" + iPage.getPages());
        List<User> userList = iPage.getRecords();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * 自定义查询分页
     */
    @Test
    public void selectMyPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 26);
        Page<User> page = new Page<>(1, 2);
        //自定义分页查询
        IPage<User> iPage = userMapper.selectUserPage(page, queryWrapper);
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        List<User> userList = iPage.getRecords();
        for (User user : userList) {
            System.out.println(user);
        }
    }
}