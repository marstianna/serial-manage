package server

import (
	"encoding/json"

	"wsr"
)

//http相关数据结构

type HttpResult struct {
	Code   int32                  `json:"code"`
	Status string                 `json:"status"`
	Data   map[string]interface{} `json:"data"`
}

func NewHttpResult() *HttpResult {
	return &HttpResult{
		Code:   wsr.ERR_SUCC,
		Status: wsr.GetErrorMsg(wsr.ERR_SUCC),
		Data:   make(map[string]interface{}),
	}
}

func (this *HttpResult) setCode(code int32) {
	this.Code = code
}

func (this *HttpResult) setStatus(status string) {
	this.Status = status
}

func (this *HttpResult) setCodeAndStatus(code int32) {
	this.setCode(code)
	this.setStatus(wsr.GetErrorMsg(code))
}

func (this *HttpResult) addData(key string, value interface{}) {
	this.Data[key] = value
}

func (this *HttpResult) toJsonString() string {
	v, err := json.Marshal(this)
	if err != nil {
		return ""
	}

	return string(v)
}
