package com.amazon;

import com.amazon.util.MD5Utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MainTest {

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        User user = new User();
//        Method[] methods = user.getClass().getMethods();
//        List<String> names = new ArrayList<>();
//        for (int i = 0; i < methods.length; i++) {
//            if (methods[i].getName().startsWith("set")) {
//                String name = methods[i].getName().substring(3, methods[i].getName().length());
//                StringBuffer stringBuffer = new StringBuffer();
//                System.out.println("name=" + name);
//                stringBuffer.append(name);
//                stringBuffer.insert(1, "_");
//                String s = stringBuffer.toString();
//                String s1 = s.toLowerCase();
//                System.out.println("stingBuffer=" + s1);
//                try {
//                    Class<?>[] parameterTypes = methods[i].getParameterTypes();
//                    for (int j=0;j<parameterTypes.length;j++){
//                        System.out.println(parameterTypes[j].getName());
//                    }
//                    methods[i].invoke(user, "qerw");
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println(GsonUtil.getGson().toJson(user));
        String str = "apple";
            String newString = MD5Utils.getMD5(str);
            System.out.println(newString);
        System.out.println(newString.equals(MD5Utils.getMD5(str)));

    }
}
