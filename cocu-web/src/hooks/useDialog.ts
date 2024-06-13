// hooks, for repeat usage
import { reactive } from 'vue'
export default function useDialog(){
    // define dialog properties
    // ref: define simple, basic reactive data type
    // reactive: define complicate(object) reactive data type
    const dialog = reactive({
        title: 'Add New',
        visible: false,
        width: 600,
        height: 200
    })
    // close dialog
    const onClose = () => {
        dialog.visible = false;
    }
    // confirm dialog
    const onConfirm = () => {
        dialog.visible = false;
    }
    // display dialog
    const onShow = () => {
        dialog.visible = true
    }
    return {
        dialog,
        onClose,
        onConfirm,
        onShow
    }
}