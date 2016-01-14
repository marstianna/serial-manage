package server

import (
	"encoding/json"

	"wsr"
)

//http相关数据结构

type ReplyBase struct {
	Code   int32                  `json:"code"`
	Status string                 `json:"status"`
	Data   map[string]interface{} `json:"data"`
}


func (this *ReplyBase) toJsonString() string {
	v, err := json.Marshal(this)
	if err != nil {
		return ""
	}

	return string(v)
}

func (this *ReplyBase) addData(key string, value interface{}) {
	this.Data[key] = value
}


//////////////////////////////////////////////////////////////////////

func NewReplySucc() *ReplyBase {
	return &ReplyBase{
		Code: wsr.ERR_SUCC,
		Status: "成功",
		Data:   make(map[string]interface{}),
	}
}

//////////////////////////////////////////////////////////////////////

func NewReplyFail(e error) *ReplyBase {
	return &ReplyBase{
		Code:   -1,
		Status: e.Error(),
		Data:   make(map[string]interface{}),
	}
}