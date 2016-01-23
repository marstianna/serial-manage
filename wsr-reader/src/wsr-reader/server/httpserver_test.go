package server

import (
	"testing"
)


func TestServer(t *testing.T) {
	if err := Run(":8088"); err != nil {
		t.Error("error:" + err.Error())
	}
}