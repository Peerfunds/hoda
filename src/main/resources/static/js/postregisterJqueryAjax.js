$(document)
		.ready(
				function() {

					// SUBMIT FORM
					$("form").submit(function(event) {
						// Prevent the form from submitting via the browser.
						event.preventDefault();

						var inputs = $(this).find('input');

						// prepare data from input-form
						var data = {
							name : $(inputs[0]).val(),
							mobile : $(inputs[1]).val(),
							email : $(inputs[2]).val(),
							acc_no : $(inputs[3]).val(),
							ifsc : $(inputs[4]).val(),
							bank_name : $(inputs[5]).val(),
							name_bank_acc : $(inputs[6]).val(),
							security_ques1 : $(inputs[7]).val(),
							security_ques2 : $(inputs[8]).val(),
							password : $(inputs[9]).val(),
							reffered_by : $(inputs[10]).val()
						}

						ajaxPost(data);

						// reset input data
						$(inputs[0]).val("");
						$(inputs[1]).val("")
						$(inputs[2]).val("");
						$(inputs[3]).val("")
						$(inputs[4]).val("");
						$(inputs[5]).val("")
						$(inputs[6]).val("");
						$(inputs[7]).val("");
						$(inputs[8]).val("");
						$(inputs[9]).val("");
						$(inputs[10]).val("");

					});

					function ajaxPost(data) {

						// DO POST
						$
								.ajax({
									type : "POST",
									contentType : "application/json",
									url : window.location + "rest/user/add",
									data : JSON.stringify(data),
									dataType : 'text',
									success : function(result) {
										$("#postResultMsg")
												.html(
														"<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>"
																+ result);
									},
									error : function(e) {
										alert("Error!")
									}
								});

					}

				});