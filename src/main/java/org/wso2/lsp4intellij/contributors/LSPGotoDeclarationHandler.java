/*
 * Copyright (c) 2023, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.lsp4intellij.contributors;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.wso2.lsp4intellij.editor.EditorEventManager;
import org.wso2.lsp4intellij.editor.EditorEventManagerBase;

/**
 * The class for handling goto Declaration or Usages
 */
class LSPGotoDeclarationHandler implements GotoDeclarationHandler {
    private static final Logger LOG = Logger.getInstance(LSPGotoDeclarationHandler.class);

    @Override
    public PsiElement @Nullable [] getGotoDeclarationTargets(@Nullable PsiElement psiElement, int offset, Editor editor) {
        try {
            EditorEventManager manager = EditorEventManagerBase.forEditor(editor);
            if (manager != null) {
                PsiElement psiElementRef = manager.definition(offset);
                if (psiElementRef != null) {
                    return new PsiElement[]{psiElementRef};
                }
            }
            return null;
        } catch (ProcessCanceledException ignored) {
            // ProcessCanceledException can be ignored.
        } catch (Exception e) {
            LOG.warn("LSP Goto declaration ended with an error", e);
        }
        return null;
    }

    @Override
    public @Nullable
    @Nls(capitalization = Nls.Capitalization.Title) String getActionText(@NotNull DataContext context) {
        return null;
    }
}
