using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using RESTfulAPI_Gamer.Areas.Api.Models;

namespace RESTfulAPI_Gamer.Areas.Api.Controllers
{
    public class PublicationsController : Controller
    {
        private PublicationsRepository publicationsRepository;
        RSAProvider RSACryptoServiceProvider = new RSAProvider();


        public PublicationsController()
        {

            publicationsRepository = new PublicationsRepository();
        
        }

        [HttpGet]
        public JsonResult Publications(int IdPublication)
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

            return Json(publicationsRepository.GetDetailPublicationById(IdPublication), JsonRequestBehavior.AllowGet);

        }


        public JsonResult Publication(int? id, Publication item)
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
                    return Json(publicationsRepository.InsertPublication(item));

                case "GET":
                    return Json(publicationsRepository.SearchPublicationById( id.GetValueOrDefault()), JsonRequestBehavior.AllowGet);
            }

            return Json(new { Error = true, Message = "Operacion HTTP desconocida" });


        }




    }
}
