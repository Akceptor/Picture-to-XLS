<HTML>
<HEAD><TITLE>Picture to Excell</TITLE>
    <script type='text/javascript' src='//code.jquery.com/jquery-1.9.1.js'></script>
    <script type='text/javascript'>
        $(window).load(function(){function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#preview').attr('src', e.target.result);
                    $('#preview').attr('width', "512px");
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
            $("#imgInp").change(function(){
                readURL(this);
            });});
    </script>
</HEAD>
<BODY>
<H1>Picture to Excell</H1>
<FORM ACTION="/" ENCTYPE="multipart/form-data" METHOD=POST>
    Select picture <INPUT TYPE=FILE NAME=file id="imgInp"><BR>
    <img id="preview" src="#" alt="Preview" width="1px"/><br>
    <hr><BR><h2>Color depth</h2>
    <INPUT TYPE=CHECKBOX NAME='DECREASE COLOR' CHECKED>Decrease to 8 bits per pixel (256 colors)</INPUT><BR><h2>Picture pixel size</h2>
    <input type="radio" name="CELL SIZE" value="PIXEL" checked>PIXEL<br>
    <input type="radio" name="CELL SIZE" value="SMALL">SMALL<br>
    <input type="radio" name="CELL SIZE" value="BIG">BIG<br>

    <INPUT TYPE=SUBMIT>
</FORM>
</BODY></HTML>
