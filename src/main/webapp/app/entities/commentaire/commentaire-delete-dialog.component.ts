import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Commentaire } from './commentaire.model';
import { CommentairePopupService } from './commentaire-popup.service';
import { CommentaireService } from './commentaire.service';

@Component({
    selector: 'jhi-commentaire-delete-dialog',
    templateUrl: './commentaire-delete-dialog.component.html'
})
export class CommentaireDeleteDialogComponent {

    commentaire: Commentaire;

    constructor(
        private commentaireService: CommentaireService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commentaireService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commentaireListModification',
                content: 'Deleted an commentaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commentaire-delete-popup',
    template: ''
})
export class CommentaireDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentairePopupService: CommentairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.commentairePopupService
                .open(CommentaireDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
