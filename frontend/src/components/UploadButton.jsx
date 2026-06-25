function UploadButton({
  setImageFile,
  setOriginalPreview,
  setProcessedPreview
}) {

  const handleUpload = (event) => {

    const file =
      event.target.files[0];

    if (!file) return;

    setImageFile(file);

    setOriginalPreview(
      URL.createObjectURL(file)
    );

    setProcessedPreview(null);
  };

  return (
    <label className="upload-btn">

      Upload Image

      <input
        type="file"
        accept="image/*"
        hidden
        onChange={handleUpload}
      />

    </label>
  );
}

export default UploadButton;