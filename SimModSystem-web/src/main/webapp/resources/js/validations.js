/* 
 * Copyright 2012 Christian Vielma <cvielma@librethinking.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This JS file is used for fields validation.
 * 
 * @author Christian Vielma <cvielma@librethinking.com>
 */


/**This function validates a form (given the form's name)
* It uses a name convention to validate the fields in the form
* @param formName the name of the form
* This will submit the form if valid.
*/
function validateForm(formName){
    var myForm = document.forms[formName];
    var error = false;
    var errorStr="error-";
    
    for(i=0; i<myForm.elements.length-1; i++){
        if(myForm.elements[i].tagName!="BUTTON"){
            error = error || validateField(myForm.elements[i].name, formName);       
        }
        
    }
    if (!error){myForm.submit();}
    else{showBlinkOrHide(error, errorStr.concat(formName));}
    
}

/**This function validates an especific field (given the field's name)
 * It uses a name convention to validate the fields in the form
 * @param fieldName name of the field.
 * @param formName name of the form where is located the field.
 * @return true if there are errors validating
 */
function validateField(fieldName, formName){
    var elem =document.getElementsByName(fieldName)[0];
    var error = "error-";
    var errorRange ="errorRange-";
    if(elem.id.indexOf("mand")!==-1){
              if(elem.value==""){
                  showBlinkOrHide(true, error.concat(elem.name));
                  return true;
              }
              else{
                  var errorsFound = false;
                  errorsFound = errorsFound || validateIfPassword(elem, formName);
                  errorsFound = errorsFound || validateIfEmail(elem, formName);
                  
                  var errorOutRange = false;
                  errorOutRange = checkRange(elem);
                  
                  if(!errorsFound){                    
                    showBlinkOrHide(false, error.concat(elem.name));                                      
                  }
                  else{
                      showBlinkOrHide(true, error.concat(elem.name));                  
                  }
                  
                  if(!errorOutRange){
                     try{showBlinkOrHide(false, errorRange.concat(elem.name));}
                     catch(Exception){}//for the fields that don't have range check 
                  }
                  else{
                      showBlinkOrHide(true, errorRange.concat(elem.name));                  
                  }
                  
                  return errorsFound || errorOutRange;
              }
    }
}


/**This function makes an error message appear, blink or disappear
* depending if it it's hidden or shown, and will disappear if 'error'
* @param error True if error present, False otherwise.
* @param elementName Is the name of the element to be affected.
* @return If it is True, it will check if the error is already showing. If it is showing
* it will blink, if not, it will show the error. Finally, if it is false it will disappear
* the error (just in the case it was shown previously)
*/
function showBlinkOrHide(error, elementName){
    var element = document.getElementById(elementName);
    if(error){
        if(element.style.display=="none"){
            Effect.Appear(element.id);
        }
        else{
            Effect.Highlight(element.id);
        }
    }
    else{
        if(element.style.display!=="none"){
            Effect.Fade(element.id);
        }
    }
    
    
}

/** 
 * This function search for a field in a form with certain substring in its ID
 * @param sstr substring to search in ID fields.
 * @param formName form name where to search for fields
 * @return a list with the elements with the matching ids
 */
function getByIDSubstring(sstr, formName){
    var myForm = document.forms[formName];
    var result = new Array();
    var currIndex = result.length;
    
    for(i=0; i<myForm.elements.length-1; i++){
        var id = myForm.elements[i].id;       
        
        if(id.indexOf(sstr)!=-1){
            result[currIndex]=myForm.elements[i];
            currIndex++;
        }
    }
    
    return result;
    
}

/**
 * This function validates if a field is a password confirm field. If so
 * then it validates that the value is the same than that from the passwd field.
 * @param elem the element to be investigated.
 * @param formName name of the form where are located the fields
 * @return return true if there is an error (paswords don't match or any other).
 * false if the field is not a password field or if the passwords match
 */
function validateIfPassword(elem, formName){
    if(elem.id.indexOf("passwdconfirm")!==-1){
        var passwds = getByIDSubstring("passwd", formName);
        if(passwds!=null && passwds.length>0){
            for(var i=0; i<passwds.length; i++){
                if(passwds[i].id.indexOf("passwdconfirm")==-1){
                    if(passwds[i].value==elem.value) //this means passwds are the same
                    {return false;}
                    else{return true;}
                }
            }
        }
        else{
            return true;//this means error
        }        
    }
    else{
        return false; //this means the field is not passwdconfirm
    }
}

/**
 * This function validates if a field is an email field. Then it validates that
 * is a valid email address.
 * @param elem the element to be investigated.
 * @param formName name of the form where are located the fields
 * @return return true if there is an error (is not a valid email address or other).
 * false if the field is not an email field or if it is a valid email address
 */
function validateIfEmail(elem, formName){
    if(elem.id.indexOf("email")!==-1){
       if(elem.value.indexOf("@")!==-1) //this means is a valid email
        {return false;}
        else
        {return true;}
     }
     else{
        return false; //this means the field is not an email field
    }
}

/** 
 * This function extract the minimum and maximum value that a field can
 * have. It treats a string using a stucture by convention. The id of the
 * field should have a string with a substring of the form: 
 *          |*typeMinValue-value*||*typeMaxValue-value*|
 * It will return an array of three records: type, minValue and maxValue:
 *          [1][0.0][9999.0]
 * The type will be as follows:
 *     -2 - Unknown type
 *     -1 - Couldn't be treated
 *      0 - String
 *      1 - Int
 *      2 - Double
 * @param id String to parse. Should be the id of the element
 * @return See above
 */
function getTypeAndRange(id){
    var treat1 = id.split("|*");
    var result = new Array();
    result[0]=-1;
    
    for(var i=0; i<treat1.length; i++){
        if(treat1[i].indexOf("*|")!==-1){
            var treat2 = (treat1[i].substr(0, treat1[i].length-2)).split("-");
            if(result[0]==-1){//type hasn't been set
                result[0]= getType(treat2[0]);
            }
            result[i]= getValue(treat2[0], treat2[1]);
        }
    }
    return result;
}

/** 
 * This function translate a string to a number type as follows:
 *      unknown  -> -2
 *      'text'   ->  0
 *      'int'    ->  1
 *      'double' ->  2
 * @param type String with the type
 * @return a number representing the type
 */
function getType(type){
    if(type=="text"){return 0;}
    else if(type=="int"){return 1;}
    else if(type=="double"){return 2;}
    else{return -2;}
}

/**
 * This function returns the value translation of the string representing the value.
 * It receives a string with the type and another string with the possible value, and
 * this returns the value translated. For example:
 *      type= "int"
 *      value="60"
 *      it will return: 60 (int)
 * If type is not recognized it will return "error-1", if value is not recognized will return "error-2"
 * @param type String with the type of the value
 * @param value String with the value representing the desired value
 * @return Desired value
 */
function getValue(type, value){
    if(type=="text"){return "";}
    else if(type=="int"){
          var parsed = parseInt(value);
          if(!isNaN(parsed)){return parsed;}
          else if(value=="max"){var MAX_INT = Math.pow(2, 53);return MAX_INT;}
          else{return "error-2";}
    }
    else if(type=="double"){
          var parsed = parseFloat(value);
          if(!isNaN(parsed)){return parsed;}
          else if(value=="max"){return Number.MAX_VALUE;}
          else if(value=="minneg"){return -1*Number.MAX_VALUE}
          else{return "error-2";}
    }
    else{return "error-1";}
    
}

/**
 * This function checks an element to determine if it has correct values.
 * This checking is done treating the string in the id field. If the treatment 
 * cannot be done ir will return false, that is the same that it is valid.
 * @param elem Element to be checked.
 * @return True if the element doensn't meet the range and false if otherwise. 
 * Also this can return false if the range check cannot be completed.
 */
function checkRange(elem){
    var typeRange = getTypeAndRange(elem.id);
    try{
        if(typeRange[0]>0){//this excludes checking strings
            //This assumes the rest of the types of fields are numeric
            if(elem.value>= typeRange[1] && elem.value <= typeRange[2]){return false}
            else{return true;}
        }
        else{return false;} //this means that the id field couldn't be checked or it is string
    }
    catch(Exception){
        return true;
    }
}

/**
 * Function to show the different states in the result.
 * @param id Number of the state to show.
 */
var currentId="1";
function showState(id){
    Effect.DropOut(currentId, 
    {afterFinish:
        function(){
            currentId=id;
        Effect.Appear(id, { duration: 1.0 });
            }
    })
        
    
    
    
}

/**
 * Function to show or hide categories of the state.
 * @param id With the name of the div to show or hide.
 * This method assumes the button's Id that called this method is the same id 
 * concatenated with "Button".
 */
var categoriesShown = new Array();
function showCategory(id){
    var shown = searchId(categoriesShown, id)
    if(shown==-1){
        Effect.SlideDown(id);
        categoriesShown.push(id);
        var button = document.getElementById(id+"Button");
        if(button!=null){button.innerHTML="^";}
        
    }
    else{
        Effect.SlideUp(id);
        categoriesShown.splice(shown, 1);
         var button = document.getElementById(id+"Button");
        if(button!=null){button.innerHTML=">";}
    }
}

/**
 * This function detects if a string is in an array
 * @param array to be searched
 * @param id string to be searched.
 * @return the position of the searched element or -1 otherwise
 */
function searchId(array, id){
    for(var i=0; i<array.length; i++){
        if(array[i]==id){return i;}
    }
    return -1;
    
}

/**
 * This function copies a parameter block in order to allow multiple values
 * of the same parameter
 * (for multiple instances parameters like fixed expenses)
 * @param id of the <div> block to be copied.
 */
var copies = 0;
function addParameterBlock(id){
    var original = document.getElementById(id);
    var clone = original.cloneNode(true); // "deep" clone
    var plusButton = clone.getElementsByTagName("button")[0];
    clone.removeChild(plusButton); //TODO: add "remove" button.
    
    clone.id = original.id+ ++copies;
    original.parentNode.insertBefore(clone, original.nextSibling);    
}
