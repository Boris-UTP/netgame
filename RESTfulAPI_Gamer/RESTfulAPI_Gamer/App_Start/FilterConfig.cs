using System.Web;
using System.Web.Mvc;

using RESTfulAPI_Gamer.Areas;
using System.Web.Http;

namespace RESTfulAPI_Gamer
{
    public class FilterConfig
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            filters.Add(new HandleErrorAttribute());

        }
    }
}