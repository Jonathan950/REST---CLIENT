/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function eliminar(id) {
    swal({
        title: "Estas seguro que quieres eliminar el registro?",
        text: "Una vez eliminado, no será posible recuperar la información!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
            .then((OK) => {
                if (OK) {
                    $.ajax({
                        url:"/eliminar/"+id,
                        succes: function(res){
                            console.log(res);
                        }
                    });
                    swal("El registro se a eliminado", {
                        icon: "success",
                    }).then((OK)=>{
                        if(OK){
                            location.href="listar";
                        }
                    });
                } else {
                    swal("Operación cancelada!");
                }
            });
}