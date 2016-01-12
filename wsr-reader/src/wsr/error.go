package wsr

var errorMap map[int32]string = make(map[int32]string, 50)

func init() {
	errorMap[-1] = "参数值不正确"

	errorMap[-10] = "(仅修改密码)装载密码失败"
	errorMap[-11] = "(仅修改密码)读数据失败"
	errorMap[-12] = "(仅修改密码)写数据失败"

	errorMap[-100] = "写串口失败"
	errorMap[-101] = "读串口失败"
	errorMap[-102] = "接收的数据无效"

	errorMap[-226] = "卡不存在/卡坏"
	errorMap[-227] = "防冲撞错误"
	errorMap[-228] = "锁定卡出错"
	errorMap[-229] = "传密钥出错"
	errorMap[-230] = "密码验证出错"
	errorMap[-231] = "读块数据出错"
	errorMap[-232] = "写块数据出错"
	errorMap[-233] = "复位 RC500 失败"
	errorMap[-234] = "钱包值调入缓冲区出错"
	errorMap[-235] = "保存缓冲区值中的钱包值出错"
	errorMap[-236] = "增值出错"
	errorMap[-238] = "减值出错"
	errorMap[-240] = "块值格式不对"
	errorMap[-241] = "块值不够减"
	errorMap[-242] = "值溢出"
}

func getErrorMsg(code int32) string {
	if code >= 0 {
		return "ok"
	}

	v, ok := errorMap[code]
	if ok {
		return v
	}

	return "unknown error"
}
