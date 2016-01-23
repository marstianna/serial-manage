package server

import (
	"encoding/json"
)

//http相关数据结构

type replyBase struct {
	Code   int32                  `json:"code"`
	Status string                 `json:"status"`
	Data   map[string]interface{} `json:"data"`
}

func (this *replyBase) toJsonString() string {
	v, err := json.Marshal(this)
	if err != nil {
		return ""
	}

	return string(v)
}

func (this *replyBase) addData(key string, value interface{}) {
	this.Data[key] = value
}

//////////////////////////////////////////////////////////////////////

func newReplySucc() *replyBase {
	return &replyBase{
		Code:   0,
		Status: "成功",
		Data:   make(map[string]interface{}),
	}
}

//////////////////////////////////////////////////////////////////////

func newReplyFail(e error) *replyBase {
	return &replyBase{
		Code:   -1,
		Status: e.Error(),
		Data:   make(map[string]interface{}),
	}
}

func newReplyFailWithString(e string) *replyBase {
	return &replyBase{
		Code:   -1,
		Status: e,
		Data:   make(map[string]interface{}),
	}
}
