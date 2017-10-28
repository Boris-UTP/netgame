using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using RESTfulAPI_Gamer.Areas.Api.Models;
using System.Net.Http;
using System.Web.Http.Controllers;
using RESTfulAPI_Gamer.Areas.Api;

namespace RESTfulAPI_Gamer.Areas.Api.Controllers
{
    public class UsersController : Controller
    {


        private UsersRepository usersRepository;
        RSAProvider RSACryptoServiceProvider = new RSAProvider();


        public UsersController()
        {

            usersRepository = new UsersRepository();
        
        }

    
        
        [HttpGet]
        public JsonResult Users(string UserName, string Password)
        {
                     
            return Json(usersRepository.SearchUserLogin(UserName, Password), JsonRequestBehavior.AllowGet);
        }


        public JsonResult User(int? id, Users item)
        {

            switch (Request.HttpMethod)
            {
                case "POST":
                    return Json(usersRepository.InsertUser(item));

                case "PUT":
                    return Json(usersRepository.UpdateUser(item));
            }

            return Json(new { Error = true, Message = "Operacion HTTP desconocida" });
            

        }







    }
}
