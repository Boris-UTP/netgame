using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using RESTfulAPI_Gamer.Areas.Api.Models;

namespace RESTfulAPI_Gamer.Areas.Api.Controllers
{
    public class CommentsController : Controller
    {

        private CommentsRepository commentsRepository;
        RSAProvider RSACryptoServiceProvider = new RSAProvider();

         public CommentsController()
        {

            commentsRepository = new CommentsRepository();
        
        }


         public JsonResult Comment(int? id, Comment item)
        {

            string token = "";

            try
            {
               token = this.Request.Headers.GetValues("Token").First();
            }
            catch (Exception)
            {
                string tokens = "Missing Token";
                return Json(tokens, JsonRequestBehavior.AllowGet);
            }

            try
            {   
                AuthorizedUserRepository.GetUsers().First(x => x.Name == RSACryptoServiceProvider.DecripText(token));

            }
            catch (Exception)
            {
                return Json("Unauthorized User", JsonRequestBehavior.AllowGet);
            }
             
             
             
             switch (Request.HttpMethod)
            {
                case "POST":
                    return Json(commentsRepository.InsertComment(item));               
            }

            return Json(new { Error = true, Message = "Operacion HTTP desconocida" });


        }




    }
}
