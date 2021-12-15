$(function () {

	// DataTable初期設定（6列目と7列目をソート対象外に設定）
	$(".task-table").DataTable({
		columns: [
			null,
			null,
			null,
			null,
			null,
			{ "orderable": false },
			{ "orderable": false }
		]
	});

	// ========================================
	// 新規作成ボタン押下
	// ========================================
	$('.register-btn').on('click', function () {

		// モーダル初期化
		resetModal();
		
		// モーダル表示
		$('#todo-modal').modal();

	});

	// ========================================
	// 編集ボタン押下
	// ========================================
	$('.edit-btn').on('click', function () {

		// 編集対象のTODO表示
		editToDo($(this));

	});

	// ========================================
	// 削除ボタン押下
	// ========================================
	$('.delete-btn').on('click', function () {

		// TODOの削除
		deleteToDo($(this));

	});

	// ========================================
	// 送信ボタン押下
	// ========================================
	$('.send-btn').on('click', function () {

		// TODOの作成/更新
		saveToDo();

	});

});

//===============================
// モーダルの初期化
//===============================
function resetModal() {

	// タスクID
	$('#todo-modal #todo-id').val('');
	// タスク種別
	$('#todo-modal #todo-task-type').val(1);
	// やること
	$('#todo-modal #todo-title').val('');
	// 詳細
	$('#todo-modal #todo-detail').val('');
	// 期限
	$('#todo-modal #todo-limit-date').val('');

}

//===============================
// 編集対象のTODO表示
//===============================
function editToDo($this) {

	// 編集対象の行のオブジェクトを取得
	let parentObj = $this.closest('tr');

	// タスクID
	$('#todo-modal #todo-id').val(parentObj.find('.task-id').text());
	// タスク種別
	$('#todo-modal #todo-task-type').val(parentObj.find('.task-type-id').val());
	// やること
	$('#todo-modal #todo-title').val(parentObj.find('.title').text());
	// 詳細
	$('#todo-modal #todo-detail').val(parentObj.find('.detail').text());
	// 期限 typeがdatetime-localはyyyy-MM-ddThh:mm形式でないと設定できないので事前に時間の変換処理を行う
	$('#todo-modal #todo-limit-date').val(convetDateToIso(parentObj.find('.format-limit-date').val()));

	// モーダル表示
	$('#todo-modal').modal();

}

//===============================
// TODOの作成/更新
//===============================
function saveToDo() {

	let title = $('#todo-modal #todo-title').val();
	let detail = $('#todo-modal #todo-detail').val();
	let limitDate = $('#todo-modal #todo-limit-date').val();

	let msg = "";

	if (title == null || title == ""){
		msg += 'やることを入力してください<br/>';
	}

	if (detail == null || detail == ""){
		msg += '詳細を入力してください<br/>';
	}

	if (limitDate == null || limitDate == ""){
		msg += '期限を入力してください<br/>';
	}

	// 入力値に不備がある場合
	if (msg != "") {
		// バリデーションエラー
		Swal.fire({
			icon: 'error',
			title: 'エラー',
			confirmButtonColor: '#fa6161 ',
			html: "<div class='text-left d-inline-block'>" + msg + "</div>",
			allowOutsideClick: false
		});
		return;
	}

	// 送信データの作成
	let data = {
		taskId:$('#todo-modal #todo-id').val(),
		taskTypeId: $('#todo-modal #todo-task-type').val(),
		title: title,
		detail: detail,
		limitDate: limitDate,
	};

	Ajax("/save", "POST", JSON.stringify(data), "application/json");

}

//===============================
// TODOの削除
//===============================
function deleteToDo($this) {

	// 削除対象の行のオブジェクトを取得
	let parentObj = $this.closest('tr');

	let msg = "";
	msg += "<label class='confirm-label'>タスク種別</label>：" + parentObj.find('.task-name').text() + "<br/>";
	msg += "<label class='confirm-label'>やること</label>：" + parentObj.find('.title').text() + "<br/>";
	msg += "<label class='confirm-label'>詳細</label>：" + parentObj.find('.detail').text() + "<br/>";
	msg += "<label class='confirm-label'>期限</label>：" + parentObj.find('.limit-date').text();

	Swal.fire({
		title: '以下のタスクを削除しますか?',
		html: "<div class='text-left d-inline-block'>" + msg + "</div>",
		icon: 'warning',
		showCancelButton: true,
		cancelButtonText: '取消',
		confirmButtonText: '削除',
		allowOutsideClick: false
	}).then((result) => {

		// 取消押下時の処理 
		if (result.isConfirmed) {

			// 送信データの作成
			let data = {
				taskId: parentObj.find('.task-id').text(),
			};

			Ajax("/delete", "POST", JSON.stringify(data), "application/json");
		}
		
	})

}

//===============================
// Ajax汎用メソッド
//===============================
function Ajax(url, type, data, contentType) {

	$.ajax({
		url: url,
		type: type,
		data : data,
		contentType: contentType
	}).then(function (data) {

		if(data.resultCode == '0'){

			// 処理成功
			Swal.fire({
				icon: 'success',
				title: '',
				confirmButtonColor: '#fa6161 ',
				html: data.msg,
				allowOutsideClick: false
			}).then(() => {
				location.reload();
			});

		} else {

			// 処理失敗
			Swal.fire({
				icon: 'error',
				title: 'エラー',
				confirmButtonColor: '#fa6161 ',
				html: data.msg,
				allowOutsideClick: false
			});

		}

	}, function () {

		// Ajax通信失敗
		Swal.fire({
			icon: 'error',
			title: 'エラー',
			confirmButtonColor: '#fa6161 ',
			html: '予期せぬエラーが発生しました。<br />お手数ですが再操作をお願いします。',
			allowOutsideClick: false
		}).then(() => {
			location.reload();
		});

	});

}

//===============================
// 日付のyyyy-MM-ddThh:mm変換
//===============================
function convetDateToIso(dateStr){

	let date = new Date(dateStr);
	let shift = date.getTime()+9*60*60*1000;
	let time = new Date(shift).toISOString().split('.')[0];

	return time;

}