function UploadSection({
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
    <div className="upload-section">

      <input
        type="file"
        accept="image/*"
        onChange={handleUpload}
      />

    </div>
  );
}

export default UploadSection;