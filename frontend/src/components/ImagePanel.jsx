function ImagePanel({
  title,
  image,
  children
}) {
  return (
    <div className="image-panel">

      <h2>{title}</h2>

      <div className="image-viewer">
        {image ? (
          <img
            src={image}
            alt={title}
            className="preview-image"
          />
        ) : (
          <div className="image-placeholder">
            No Image
          </div>
        )}
      </div>

      {children}

    </div>
  );
}

export default ImagePanel;