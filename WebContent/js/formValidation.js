/**
 * Validation for forms.
 */
function taskFormValidation() {
	$('#taskForm').bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			task_name: {
				message: 'Please enter a name',
				validators: {
					notEmpty: {
						message: 'The task name cannot be empty'
					},
					stringLength: {
						min: 6,
						max: 30,
						message: 'The task name must be more than 6 and less than 30 characters long'
					}
				}
			},
			description: {
				validators: {
					notEmpty: {
						message: 'Please enter a description for the task.'
					},
					stringLength: {
						min: 10,
						max: 1000,
						message: 'The task description should be descriptive.'
					}
				}
			}
		}
	});
}


function userFormValidation() {
	$('#userForm').bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			user_name: {
				message: 'Please enter a user name',
				validators: {
					notEmpty: {
						message: 'The user name cannot be empty'
					},
					stringLength: {
						min: 4,
						max: 30,
						message: 'The user name must be more than 3 and less than 30 characters long'
					}
				}
			},
			first_name: {
				message: 'Please enter a first name',
				validators: {
					notEmpty: {
						message: 'The first name cannot be empty'
					},
					stringLength: {
						min: 1,
						max: 30,
						message: 'The first name must be at least one character and less than 30 characters long'
					}
				}
			},
			last_name: {
				message: 'Please enter a last name',
				validators: {
					notEmpty: {
						message: 'The last name cannot be empty'
					},
					stringLength: {
						min: 1,
						max: 30,
						message: 'The last name must be at least one character and less than 30 characters long'
					}
				}
			},
			email: {
				validators: {
					notEmpty: {
						message: 'Please enter an email address for the user.'
					},
					emailAddress: {
						message: 'That is not a valid email address.'
					}
				}
			}
		}
	});	
}

function projectFormValidation() {
	$('#projectForm').bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			project_name: {
				message: 'Please enter a name',
				validators: {
					notEmpty: {
						message: 'The project name cannot be empty'
					},
					stringLength: {
						min: 6,
						max: 30,
						message: 'The project name must be more than 6 and less than 30 characters long'
					}
				}
			},
			planned_start_date: {
				message: 'Please select a start date',
				validators: {
					notEmpty: {
						message: 'The project start date cannot be empty'
					},
				}
			},
			planned_end_date: {
				message: 'Please select an end date',
				validators: {
					notEmpty: {
						message: 'The project end date cannot be empty'
					},
				}
			},
			description: {
				validators: {
					notEmpty: {
						message: 'Please enter a description for the project.'
					},
					stringLength: {
						min: 10,
						max: 1000,
						message: 'The project description should be descriptive.'
					}
				}
			},
			
		}
	});	
}

function sprintFormValidation() {
	$('#sprintForm').bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			sprint_name: {
				message: 'Please enter a name',
				validators: {
					notEmpty: {
						message: 'The sprint name cannot be empty'
					},
					stringLength: {
						min: 6,
						max: 30,
						message: 'The sprint name must be more than 6 and less than 30 characters long'
					}
				}
			},
			start_date: {
				message: 'Please select a start date',
				validators: {
					notEmpty: {
						message: 'The sprint start date cannot be empty'
					},
				}
			},
			end_date: {
				message: 'Please select an end date',
				validators: {
					notEmpty: {
						message: 'The sprint end date cannot be empty'
					},
				}
			},
			description: {
				validators: {
					notEmpty: {
						message: 'Please enter a description for the sprint.'
					},
					stringLength: {
						min: 10,
						max: 1000,
						message: 'The sprint description should be descriptive.'
					}
				}
			}
		}
	});
}

function storyFormValidation() {
	$('#storyForm').bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			story_name: {
				message: 'Please enter a name',
				validators: {
					notEmpty: {
						message: 'The story name cannot be empty'
					},
					stringLength: {
						min: 6,
						max: 30,
						message: 'The story name must be more than 6 and less than 30 characters long'
					}
				}
			},
			description: {
				validators: {
					notEmpty: {
						message: 'Please enter a description for the story.'
					},
					stringLength: {
						min: 10,
						max: 1000,
						message: 'The story description should be descriptive.'
					}
				}
			},
			acceptance_criteria: {
				validators: {
					notEmpty: {
						message: 'Please enter an acceptance criteria for the story.'
					},
					stringLength: {
						min: 10,
						max: 1000,
						message: 'The acceptance criteria should be descriptive.'
					}
				}
			}
		}
	});	
}

function uploadFormValidation() {
	$('#uploadForm').bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			selected_file: {
				message: 'Please select a file.',
				validators: {
					notEmpty: {
						message: 'A file must be selected.'
					},
					file: {
                        extension: 'jpeg,png,txt,pdf,doc,rtf,xls,ppt,zip,gif',
                        type: 'image/jpeg,image/png,text/plain,application/pdf,application/msword,application/rtf'+
                        'application/vnd.ms-excel,application/vnd.ms-powerpoint,pplication/x-rar-compressed,image/gif',
                        maxSize: 1048576*10,   // 25 MB
                        message: 'The selected file is not valid'
                    }
				}
			},
			description: {
				validators: {
					notEmpty: {
						message: 'Please enter a description for the file.'
					},
					stringLength: {
						min: 10,
						max: 1000,
						message: 'The file description should be descriptive.'
					}
				}
			}
		}
	});	
}
