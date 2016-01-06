var privilege = {
    globalVariable:{
        channelManagerId:"channelManager",    //通道管理顶端元素Id
        queryServiceId:"queryService",        //数据服务顶端元素Id
        systemManagerId:"systemManager",       //系统管理顶端元素Id
        approvalBtnClass:"admin"               //审批按钮Class元素
    },
    fn:{
        initPrivilege:function(){
            if(common.globalVariable.userType == 'LEADER'){
                $("#"+privilege.globalVariable.systemManagerId).css("display","none");
            }else if(common.globalVariable.userType == 'DEV'){
                $("#"+privilege.globalVariable.channelManagerId).css("display","none");
                $("#"+privilege.globalVariable.systemManagerId).css("display","none");
            }
        },
        hiddenApprovalBtn:function(){
            if(common.globalVariable.userType == 'LEADER' || common.globalVariable.userType == 'DEV'){
                $("button").filter(".admin").css("display","none");;
            }
        }
    }
}
$(document).ready(function(){
    privilege.fn.initPrivilege();
})