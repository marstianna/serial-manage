function getCardId(){
    $.ajax({
        url : '127.0.0.1:8086/readCardId',
        type : 'post',
        dataType : 'json',
        success : function(data){
            $("#cardId").val(JSON.stringify(data));
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert(XMLHttpRequest.responseText);
        }
    })
}

function createNewCard(){
    $.ajax({
        url : '$ctx/valuecard/addCard',
        type : 'post',
        data : $("#valueCardAdd").serialize(),
        dataType : 'json',
        async: false,
        success : function(data){
            writeUuidIntoCard(data['result']['cardUuid'])
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            $("#cardId").html(XMLHttpRequest.responseText);
        }
    })
}

function writeUuidIntoCard(uuid){
    $.ajax({
        url : '127.0.0.1:8086/writeCardUuid',
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
        url : 'http://localhost:8080/test/getCardInfo',
        type : 'post',
        dataType : 'json',
        async: false,
        success : function(data){
            alert(data['cardUuid']);
            result = data;
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            result = XMLHttpRequest.responseText;
        }
    });
    $('#cardId').attr('value',result['cardId']);
    $('#cardUuid').attr('value',result['cardUuid']);
}