package cn.ainannan;

public class Test {
	/*
	 java 利用反射机制,获取实体所有属性和方法,并对属性赋值
	 标签： javamethodsreflection
	 2016-09-02 01:24 399人阅读 评论(0) 收藏 举报
	  分类： IT-编程语言-Java（33）  
	 一个普通的实体Person:

	 复制代码
	 private int id;
	 private String name;
	 private Date createdTime;
	 ...
	 //其它字段
	 // get set方法
	 ...............
	 复制代码
	 现在需要把通过webService传过来的实体Person里面的所有字段的null值,换成""
	 实现思路:
	 1.获取实体的所有字段,遍历
	 2.获取字段类型
	 3.调用字段的get方法,判断字段值是否为空
	 4.如果字段值为空,调用字段的set方法,为字段赋值
	 code:

	 复制代码
	         Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
	         try {
	             for (int j = 0; j < field.length; j++) { // 遍历所有属性
	                 String name = field[j].getName(); // 获取属性的名字
	                 name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
	                 String type = field[j].getGenericType().toString(); // 获取属性的类型
	                 if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
	                     Method m = model.getClass().getMethod("get" + name);
	                     String value = (String) m.invoke(model); // 调用getter方法获取属性值
	                     if (value == null) {
	                         m = model.getClass().getMethod("set"+name,String.class);
	                         m.invoke(model, "");
	                     }
	                 }
	                 if (type.equals("class java.lang.Integer")) {
	                     Method m = model.getClass().getMethod("get" + name);
	                     Integer value = (Integer) m.invoke(model);
	                     if (value == null) {
	                         m = model.getClass().getMethod("set"+name,Integer.class);
	                         m.invoke(model, 0);
	                     }
	                 }
	                 if (type.equals("class java.lang.Boolean")) {
	                     Method m = model.getClass().getMethod("get" + name);
	                     Boolean value = (Boolean) m.invoke(model);
	                     if (value == null) {
	                         m = model.getClass().getMethod("set"+name,Boolean.class);
	                         m.invoke(model, false);
	                     }
	                 }
	                 if (type.equals("class java.util.Date")) {
	                     Method m = model.getClass().getMethod("get" + name);
	                     Date value = (Date) m.invoke(model);
	                     if (value == null) {
	                         m = model.getClass().getMethod("set"+name,Date.class);
	                         m.invoke(model, new Date());
	                     }
	                 }
	 　　　　　　　　　// 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
	             }
	         } catch (NoSuchMethodException e) {
	             e.printStackTrace();
	         } catch (SecurityException e) {
	             e.printStackTrace();
	         } catch (IllegalAccessException e) {
	             e.printStackTrace();
	         } catch (IllegalArgumentException e) {
	             e.printStackTrace();
	         } catch (InvocationTargetException e) {
	             e.printStackTrace();
	         }
	         
	*/
}
