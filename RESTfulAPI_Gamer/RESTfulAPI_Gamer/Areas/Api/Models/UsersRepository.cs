using System;
using System.Collections.Generic;
using System.Data.Objects.SqlClient;
using System.Linq;
using System.Web;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{


    public class UsersRepository
    {

        static BDGamerEntities dataContext = new BDGamerEntities();
        RSAProvider RSACryptoServiceProvider = new RSAProvider();

        public  string  SearchUserLogin(string userName,string pass)
        {

            string[] queryArray;
            string tokenEncrip="";
           
            try
            {
               
            var query = from s in dataContext.Users
                            where s.UserName == userName && s.Password == pass
                            select SqlFunctions.StringConvert((double)s.IdUser).Trim() + "|" + s.UserName;

            queryArray = query.ToArray();       

            tokenEncrip = RSACryptoServiceProvider.EncripText(queryArray[0].ToString());

            }
            catch (Exception)
            {
               
            }
            


            //string token = queryArray[0].ToString();
            //var query = from u in dataContext.Users
            //            where u.UserName == userName && u.Password == pass  
            //            select new User
            //            {
            //                UserName = u.UserName,
            //                Password = u.Password,
            //            };


            return tokenEncrip; // query.SingleOrDefault();        
        }

     
        public  bool InsertUser(Users user)
        {

            try
            {
            dataContext.Users.Add(user);
            dataContext.SaveChanges();


            return true;
            }
            catch (Exception)
            {
                return false;
            }


        }

        public bool UpdateUser(Users user)
        {
           
            try
            {
            var usu = (from Users in dataContext.Users
                       where Users.IdUser == user.IdUser
                       select Users).SingleOrDefault();
            usu.UserName = user.UserName;
            usu.Password = user.Password;
            dataContext.SaveChanges();
            return true;
            }
            catch (Exception)
            {         
                return false;
            }

        }

        public class User
        {


            public string UserName { get; set; }
            public string Password { get; set; }
          //  public string token { get; set; }
      

        }



    
    }
}