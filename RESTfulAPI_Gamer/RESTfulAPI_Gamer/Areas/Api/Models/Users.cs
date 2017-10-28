//------------------------------------------------------------------------------
// <auto-generated>
//    Este código se generó a partir de una plantilla.
//
//    Los cambios manuales en este archivo pueden causar un comportamiento inesperado de la aplicación.
//    Los cambios manuales en este archivo se sobrescribirán si se regenera el código.
// </auto-generated>
//------------------------------------------------------------------------------

namespace RESTfulAPI_Gamer.Areas.Api.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class Users
    {
        public Users()
        {
            this.Assistance = new HashSet<Assistance>();
            this.Cabin = new HashSet<Cabin>();
            this.Comment = new HashSet<Comment>();
            this.Gamer = new HashSet<Gamer>();
            this.Publication = new HashSet<Publication>();
            this.UserComment = new HashSet<UserComment>();
            this.UserPublication = new HashSet<UserPublication>();
        }
    
        public int IdUser { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public Nullable<int> UserType { get; set; }
        public Nullable<int> State { get; set; }
    
        public virtual ICollection<Assistance> Assistance { get; set; }
        public virtual ICollection<Cabin> Cabin { get; set; }
        public virtual ICollection<Comment> Comment { get; set; }
        public virtual ICollection<Gamer> Gamer { get; set; }
        public virtual ICollection<Publication> Publication { get; set; }
        public virtual ICollection<UserComment> UserComment { get; set; }
        public virtual ICollection<UserPublication> UserPublication { get; set; }
    }
}