/** form data 를 JSON 형태로 변환하여 요청한다
 *  url : 요청 url
 *  formData : 요청 formData */
async function postFormToJson(url, formData) {

    // 한 가지씩 순회하며 Object 로 변환
    let formDataObject = Object.fromEntries(formData.entries());
    // Object 를 JSON String 문자열로 변환
    let formDataJsonString = JSON.stringify(formDataObject);

    // HTTP 요청 셋팅
    let fetchOptions = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: formDataJsonString,
    };

    // API 요청에 대한 응답이 올 때까지 대기
    let res = await fetch(url, fetchOptions);

    // OK 응답이 아닐 경우 디버깅을 위해 에러 throw
    if (!res.ok) {
        alert("요청에 실패하였습니다.");
        let error = await res.text();
        throw new Error(error);
    }
    // 성공시 JSON 응답을 javascript 객체로 파싱 후 반환
    return res.json();

}