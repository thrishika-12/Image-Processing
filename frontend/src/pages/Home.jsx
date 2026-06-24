import { useState } from "react";
import Header from "../components/Header";
import UploadSection from "../components/UploadSection";
import ImageViewer from "../components/ImageViewer";
import OperationsPanel from "../components/OperationsPanel";
import LayerImagePanel from "../components/LayerImagePanel";
import DownloadButton from "../components/DownloadButton";
import "../styles/Home.css";

function Home() {

  const [imageFile, setImageFile] =
    useState(null);

  const [originalPreview,
    setOriginalPreview] =
    useState(null);

  const [processedPreview,
    setProcessedPreview] =
    useState(null);

  return (
    <div className="home">

      <Header />

      <UploadSection
        setImageFile={setImageFile}
        setOriginalPreview={
          setOriginalPreview
        }
        setProcessedPreview={
          setProcessedPreview
        }
      />

      <div className="preview-section">

        <ImageViewer
          title="Original Image"
          image={originalPreview}
        />

        <ImageViewer
          title="Processed Image"
          image={processedPreview}
        />

      </div>

      <OperationsPanel
        imageFile={imageFile}
        setProcessedPreview={
          setProcessedPreview
        }
      />

      <LayerImagePanel
        setProcessedPreview={
          setProcessedPreview
        }
      />

      <DownloadButton
        processedPreview={
          processedPreview
        }
      />

    </div>
  );
}

export default Home;