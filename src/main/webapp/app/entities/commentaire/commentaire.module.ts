import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KekenSharedModule } from '../../shared';
import {
    CommentaireService,
    CommentairePopupService,
    CommentaireComponent,
    CommentaireDetailComponent,
    CommentaireDialogComponent,
    CommentairePopupComponent,
    CommentaireDeletePopupComponent,
    CommentaireDeleteDialogComponent,
    commentaireRoute,
    commentairePopupRoute,
} from './';

const ENTITY_STATES = [
    ...commentaireRoute,
    ...commentairePopupRoute,
];

@NgModule({
    imports: [
        KekenSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CommentaireComponent,
        CommentaireDetailComponent,
        CommentaireDialogComponent,
        CommentaireDeleteDialogComponent,
        CommentairePopupComponent,
        CommentaireDeletePopupComponent,
    ],
    entryComponents: [
        CommentaireComponent,
        CommentaireDialogComponent,
        CommentairePopupComponent,
        CommentaireDeleteDialogComponent,
        CommentaireDeletePopupComponent,
    ],
    providers: [
        CommentaireService,
        CommentairePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KekenCommentaireModule {}
