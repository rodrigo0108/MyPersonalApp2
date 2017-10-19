package com.rramos.mypersonalapp2.repositories;

import com.orm.SugarRecord;
import com.rramos.mypersonalapp2.models.User;

import java.util.List;

/**
 * Created by RODRIGO on 16/10/2017.
 */

public class UserRepository {

    public static List<User> list(){
        List<User> users = SugarRecord.listAll(User.class);
        return users;
    }

    public static User read(Long id){
        User user = SugarRecord.findById(User.class, id);
        return user;
    }

    public static User create(String fullname, String email, String password){
        User user = new User(fullname, email, password);
        SugarRecord.save(user);
        return user;
    }

    public static void update(String fullname, String email, String password, Long id){
        User user = SugarRecord.findById(User.class, id);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        SugarRecord.save(user);
    }

    public static User update_fullname(String fullname, Long id){
        User user = SugarRecord.findById(User.class, id);
        user.setFullname(fullname);
        SugarRecord.save(user);
        return user;
    }
    public static void delete(Long id){
        User user = SugarRecord.findById(User.class, id);
        SugarRecord.delete(user);
    }

    public static User validaEmail(String email){
        List<User> users= list();
        User user_escogido = null;
        for (User user: users
                ) {
            if(email.equals(user.getEmail())){
                user_escogido=user;
                break;
            }else{
                user_escogido=null;
            }

        }
        return user_escogido;
    }
    public static int login(String email,String password){
        List<User> users= list();
        int estado=0;
        User email_exis = validaEmail(email);

        if(email_exis!=null){
            for (User user: users
                    ) {
                if(email.equals(user.getEmail()) && password.equals(user.getPassword())){
                    estado=user.getId().intValue();
                    break;
                }else{
                    estado=2;
                }
            }
        }else{
            estado=0;
        }

        return estado;
    }



}