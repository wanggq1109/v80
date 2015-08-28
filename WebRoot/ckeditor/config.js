/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.filebrowserBrowseUrl =  '/v80/ckfinder/ckfinder.html' ;  
    config.filebrowserImageBrowseUrl =  '/v80/ckfinder/ckfinder.html?type=Images' ;  
    config.filebrowserFlashBrowseUrl =  '/v80/ckfinder/ckfinder.html?type=Flash' ;  
    config.filebrowserUploadUrl =  '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files' ;  
    config.filebrowserImageUploadUrl =  '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images' ;  
    config.filebrowserFlashUploadUrl =  '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash' ;  
    config.filebrowserWindowWidth = '2000';  
    config.filebrowserWindowHeight = '1000';
    config.language =  "zh-cn" ;  
};
