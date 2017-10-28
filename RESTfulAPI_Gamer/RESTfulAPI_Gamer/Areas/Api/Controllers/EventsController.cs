using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using RESTfulAPI_Gamer.Areas.Api.Models;

namespace RESTfulAPI_Gamer.Areas.Api.Controllers
{
    public class EventsController : Controller 
    {


        private EventsRepository eventsRepository;
        RSAProvider RSACryptoServiceProvider = new RSAProvider();


         public EventsController()
        {

            eventsRepository = new EventsRepository();
        
        }

        [HttpGet]
         public JsonResult GetAllEvents()
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


            return Json(eventsRepository.GetAllEvents(), JsonRequestBehavior.AllowGet);
        }


        public JsonResult Event(int? id, Event item)
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
                    return Json(eventsRepository.InsertEvent(item));

                case "GET":
                    return Json(eventsRepository.GetDetailEventById(id.GetValueOrDefault()), JsonRequestBehavior.AllowGet);

            }

            return Json(new { Error = true, Message = "Operacion HTTP desconocida" });


        }



    }
}
