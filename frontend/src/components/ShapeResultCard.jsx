function ShapeResultCard({
  shapeResult
}) {

  if (!shapeResult) return null;

  return (
    <div className="shape-card">

      <h3>
        Shape Detection Result
      </h3>

      <div className="shape-row">
        <strong>Width:</strong>
        <span>
          {shapeResult.width}px
        </span>
      </div>

      <div className="shape-row">
        <strong>Height:</strong>
        <span>
          {shapeResult.height}px
        </span>
      </div>

      <div className="shape-row">
        <strong>Shape:</strong>
        <span>
          {shapeResult.shape}
        </span>
      </div>

    </div>
  );
}

export default ShapeResultCard;