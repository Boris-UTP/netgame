using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace RESTfulAPI_Gamer.Controllers
{
    public class ClienteController : Controller
    {
        //
        // GET: /Cliente/

        public ActionResult Index()
        {
            return View();
        }

        // GET api/values/5
        public string Get(int id)
        {
            return "value";
        }

    }
}
