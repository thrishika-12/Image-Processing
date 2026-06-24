function ImageViewer({
  title,
  image
}) {

  return (
    <div className="image-viewer">

      <h2>{title}</h2>

      {image ? (

        <img
          src={image}
          alt={title}
          className="preview-image"
        />

      ) : (

        <div className="placeholder">
          No Image
        </div>

      )}

    </div>
  );
}

export default ImageViewer;