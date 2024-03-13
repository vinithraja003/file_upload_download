
	
let val;
 var tabledata="";
 function uploadFile() {
		
		let formData = new FormData(); 
         formData.append("file", fileupload.files[0]);
		  $.ajax({
        url: "/upload",
        type: 'POST',
        data: formData,
        async: false,
        success: function (data) {
			alert("file Uploaded")
           // alert(data)
            val=data;
        },
        cache: false,
        contentType: false,
        processData: false
    });
  console.log(val); 
  download(); 
    return false;
			
	
 
        
}	
    function download(){
		
          window.location.href = '/download/'+val;
    }
    
    
    
 //Form 
 
 function formdata(){
	 
	 var frm ={
		 name:$('#name').val(),
		 age:$('#age').val(),
		 gender:$('input[name="gender"]:checked').val(),
		 district:$('#district').val(),
		 
	 } 
	 console.log(frm);
	       $.ajax({
            type: 'post',
            url: '/frm_upload',
            data: JSON.stringify(frm),
            contentType: "application/json; charset=utf-8",
            traditional: true,
            success: function (data) {
                console.log(data);
                
                
                tabledata+='<tr>'+
				                   '<td>'+data.name+'</td>'+
				                   '<td>'+ data.age   +'</td>'+
				                   '<td>'+ data.gender   +'</td>'+
				                    '<td>'+data.district  +'</td>'+
				          '</tr>';
							 
			
			     $('#show_table > tbody').html(tabledata);
               
            }
        });
        $('#name').val("");
		 $('#age').val("");
		 $("#gender").prop('checked', false); 
		 $('#district').val("");
 }
 
 
 
 function formtable(){
	 
	        $.ajax({
               type: 'get',
               url: '/alldata',
               success:function(response){
			      /*   console.log(response.length);
			         console.log(response[0]);
			        */			         
			           	  for(let i=0;i<response.length;i++){
								 let myArray = response[i].split(",");
			                  
			                   tabledata+='<tr>'+
				                   '<td>'+myArray[0] +'</td>'+
				                   '<td>'+ myArray[1]   +'</td>'+
				                   '<td>'+ myArray[2]   +'</td>'+
				                    '<td>'+myArray[3]  +'</td>'+
				          '</tr>';
							 }
			
			     $('#show_table > tbody').html(tabledata);
			
		},
		error:function(response){
			console.log("error",response);
		}
	});
	 
 }
		