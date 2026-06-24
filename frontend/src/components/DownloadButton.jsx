function DownloadButton({
  processedPreview
}) {

  const downloadImage = () => {

    if (!processedPreview) {
      alert(
        "No processed image available"
      );
      return;
    }

    const link =
      document.createElement("a");

    link.href =
      processedPreview;

    link.download =
      "processed-image.png";

    link.click();
  };

  return (
    <button
      className="download-btn"
      onClick={downloadImage}
    >
      Download Image
    </button>
  );
}

export default DownloadButton;