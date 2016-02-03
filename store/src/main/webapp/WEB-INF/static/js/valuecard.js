function getCardId(){
    $.ajax({
        url : 'http://120.24.7.81:8080/super-admin/test/readCard',
        type : 'post',
        dataType : 'json',
        success : function(data){
            var cardId = data['data']['cardId'];
            $('#cardId').attr('value',cardId);
            $('#cardId').val(cardId)
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert(XMLHttpRequest.responseText);
        }
    })
}

function writeUuidIntoCard(uuid){
    $.ajax({
        url : 'http://120.24.7.81:8080/super-admin/test/writeCard',
        type : 'post',
        data : {
            cardUuid : uuid
        },
        dataType : 'json',
        async: false,
        success : function(data){
            $("#cardId").html(JSON.stringify(data));
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            $("#cardId").html(XMLHttpRequest.responseText);
        }
    })
}

function getCardInfoFromCard(){
    var result;
    $.ajax({
        url : 'http://120.24.7.81:8080/super-admin/test/readCard',
        type : 'post',
        dataType : 'json',
        async: false,
        success : function(data){
            alert(data['data']['cardUuid']);
            result = data;
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            result = XMLHttpRequest.responseText;
        }
    });
    $('#cardId').attr('value',result['data']['cardId']);
    $('#cardUuid').attr('value',result['data']['cardUuid']);
}