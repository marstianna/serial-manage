package server

import (
	"net/http"
	"io"
)

func Run(addr string) error {
	http.HandleFunc("/test", test)
	http.HandleFunc("/readCard", readCard)
	if err := http.ListenAndServe(addr, nil); err != nil {
		return err
	}
	
	return nil
}

type httpResult struct {
	code int
	status string
	data map[string]interface{}
}

func test(w http.ResponseWriter, req *http.Request) {
	io.WriteString(w, "hi, test")
}

//读取卡号和uuid
func readCard(w http.ResponseWriter, req *http.Request) {
	
}
