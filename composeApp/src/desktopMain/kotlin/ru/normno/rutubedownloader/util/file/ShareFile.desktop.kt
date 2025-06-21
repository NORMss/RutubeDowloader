package ru.normno.rutubedownloader.util.file

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.path

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.DataFlavor
import java.io.File

actual suspend fun shareFile(fileKit: FileKit, file: PlatformFile) {
    copyFileToClipboard(file)
}

fun copyFileToClipboard(file: PlatformFile) {
    try {
        val fileObj = File(file.path)
        if (!fileObj.exists()) {
            println("File not found: ${file.path}")
            return
        }

        val fileList = listOf(fileObj)
        val transferable = FileTransferable(fileList)

        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(transferable, null)

        println("The file has been copied to the clipboard: ${file.path}")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

class FileTransferable(private val files: List<File>) : Transferable {
    override fun getTransferDataFlavors(): Array<DataFlavor> {
        return arrayOf(DataFlavor.javaFileListFlavor)
    }

    override fun isDataFlavorSupported(flavor: DataFlavor): Boolean {
        return flavor == DataFlavor.javaFileListFlavor
    }

    override fun getTransferData(flavor: DataFlavor): Any {
        if (!isDataFlavorSupported(flavor)) {
            throw UnsupportedOperationException("Unsupported flavor: $flavor")
        }
        return files
    }
}
