import { useState } from "react";

import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import UploadButton from "../components/UploadButton";
import DownloadButton from "../components/DownloadButton";
import ImagePanel from "../components/ImagePanel";
import OperationControls from "../components/OperationControls";
import ShapeResultCard from "../components/ShapeResultCard";

import "../styles/Dashboard.css";
import "../styles/Sidebar.css";
import "../styles/ImagePanel.css";
function Dashboard() {

  const [imageFile, setImageFile] = useState(null);

  const [originalPreview, setOriginalPreview] = useState(null);

  const [processedPreview, setProcessedPreview] = useState(null);

  const [selectedOperation, setSelectedOperation] =
    useState("brightness");

  const [shapeResult, setShapeResult] = useState(null);

  return (
    <div className="dashboard">

      <Header />

      <div className="dashboard-body">

        <Sidebar
          selectedOperation={selectedOperation}
          setSelectedOperation={setSelectedOperation}
          imageFile={imageFile}
          setProcessedPreview={setProcessedPreview}
          setShapeResult={setShapeResult}
        />

        <div className="main-content">

          <div className="image-section">

            <div className="image-column">

              <div className="top-bar">
                <UploadButton
                  setImageFile={setImageFile}
                  setOriginalPreview={setOriginalPreview}
                  setProcessedPreview={setProcessedPreview}
                />
              </div>

              <ImagePanel
                title="Original Image"
                image={originalPreview}
              />

            </div>

            <div className="image-column">

              <div className="top-bar">
                <DownloadButton
                  processedPreview={processedPreview}
                />
              </div>

              <ImagePanel
                title="Processed Image"
                image={processedPreview}
              >
                {selectedOperation === "shape" && (
                  <ShapeResultCard
                    shapeResult={shapeResult}
                  />
                )}
              </ImagePanel>

            </div>

          </div>

        </div>

      </div>

    </div>
  );
}

export default Dashboard;